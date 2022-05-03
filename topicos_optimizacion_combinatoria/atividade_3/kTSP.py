from collections import namedtuple
from datetime import datetime
from itertools import combinations
from math import ceil, sqrt
import sys
from traceback import print_tb
import gurobipy as gp
import networkx as nx
from gurobipy import *

import copy

Coord = namedtuple(
    'Coord',

    'x1 '
    'y1 '
    'x2 '
    'y2 '
)

#Estrutura para resolver a heuristica
 
class UnionFind:
 
    def __init__(self, n):
        self.n = n
        self.nSets = n
        self.sz = [0 for i in range(n)]
        self.dad = [i for i in range(0, n)]
 
    def findSet(self, u):
        if u == self.dad[u]:
            return u
        self.dad[u] = self.findSet(self.dad[u])
        return self.dad[u] 
 
    def unionSet(self, u, v):
        u = self.findSet(u)
        v = self.findSet(v)
        if u != v:
            if self.sz[u] < self.sz[v]:
                swap(self.sz[u], self.sz[v])
            self.sz[u] += self.sz[v]
            self.dad[v] = self.dad[u]
            self.nSets -= 1
        return u
 
    def isConnected(self, u, v):
        return self.findSet(u) == self.findSet(v)
 
class Graph:
    #Uf: Union Find para testar conectividade
    #k: Número mínimo de arestas duplicadas
    #wG1: Dicionário com os pesos do grafo 1
    #wG2: Dicionário com os pesos do grafo 2
    #w1: Dicionário com os pesos do TSP1
    #w2: Dicionário com os pesos do TSP2
    #adj: Grafo que estamos construindo
    #degree(v): Grau do vértice v no gráfico que estamos construindo
    #intersections: Dicionário que contém as arestas repetidas dos dois grafos
    #isOne: variável booleana que indica se eu estou usando o grafo 1
    #cost1: Custo do TSP1
    #cost2: Custo do TSP2
    #cycle: Ciclo Hamiltoniano do grafo que estamos construindo
    #cycleCost: custo do ciclo hamiltoniano do grafo que construimos
    def __init__(self, k, n, wG1, wG2, w1, w2, intersections):
        self.Uf = UnionFind(n)
        self.k = k
        self.wG1 = wG1
        self.wG2 = wG2
        self.w1 = w1
        self.w2 = w2
        self.adj = [[] for i in range(n)]
        self.degree = [0 for i in range(n)]
        self.intersections = intersections
        self.cost1 = 0
        self.cost2 = 0
        self.cycle = []
        self.cycleCost = 0
 
    def addEgde(self, u, v):
        self.adj[u].append(v)
        self.adj[v].append(u)
        self.degree[u] += 1
        self.degree[v] += 1
 
    #Retorna True se o TSP1 for mais barato
    def calculateCosts(self):
        for e in self.w1:
            self.cost1 += self.w1[e] * self.wG1[e] 
        for e in self.w2:
            self.cost2 += self.w2[e] * self.wG2[e]
        self.isOne = self.cost1 < self.cost2
        return self.isOne
 
    def dfs(self, u, p):
        self.cycle.append(u)
        for v in self.adj[u]:
            if v != p:
                self.dfs(v, u)
 
    #If flag == True usaremos as arestas do grafo 1
    def calculateCycleCost(self, graphEdges):
        for i in range(0, len(self.cycle) - 1):
            u = self.cycle[i]
            v = self.cycle[i + 1]
            w = graphEdges[ (u, v) ]
            self.cycleCost += w
        u = self.cycle[0]
        v = self.cycle[len(self.cycle) - 1]
        w = graphEdges[ (u, v) ]
        self.cycleCost += w
 
    def insertEdgesOnGraph(self, edges):
        for u, v in edges:
            if not self.Uf.isConnected(u, v) and self.degree[u] < 2 and self.degree[v] < 2 and self.k > 0:
                self.k -= 1
                self.Uf.unionSet(u, v)
                self.addEgde(u, v)
 
    def completeGraph(self, edges):
        for u, v in edges.keys():
            if not self.Uf.isConnected(u, v) and self.degree[u] < 2 and self.degree[v] < 2:
                self.Uf.unionSet(u, v)
                self.addEgde(u, v)
 
    def compute_upper_bound_heuristic(self):
        self.intersections = dict(sorted(self.intersections.items(), key = lambda item: item[1]))
        self.insertEdgesOnGraph(self.intersections)
 
        graphEdges = copy.deepcopy(self.wG1) if self.calculateCosts() else copy.deepcopy(self.wG2)
        edges = self.w1 if self.isOne else self.w2
        edges = dict(sorted(edges.items(), key = lambda item: item[1]))
        self.insertEdgesOnGraph(edges)
        
        graphEdges = dict(sorted(graphEdges.items(), key = lambda item: item[1]))
        self.completeGraph(graphEdges)
        self.dfs(0, -1)
 
        self.calculateCycleCost(graphEdges)
        return min(self.cycleCost + self.cost1, self.cycleCost + self.cost2)

def subtourelim(model, where):
    if where == GRB.Callback.MIPSOL:
        valsTsp1 = model.cbGetSolution(model._x1)
        valsTsp2 = model.cbGetSolution(model._x2)

        tour = subtour(valsTsp1)
        if len(tour) < n:
            model.cbLazy(gp.quicksum(model._x1[i, j] for i, j in combinations(tour, 2)) <= len(tour) - 1)

        tour = subtour(valsTsp2)
        if len(tour) < n:
            model.cbLazy(gp.quicksum(model._x2[i, j] for i, j in combinations(tour, 2)) <= len(tour) - 1)

def subtour(vals):
    edges = gp.tuplelist((i, j) for i, j in vals.keys() if vals[i, j] > 0.5)
    unvisited = list(range(n))
    cycle = range(n + 1)
    while unvisited:
        thiscycle = []
        neighbors = unvisited
        while neighbors:
            current = neighbors[0]
            thiscycle.append(current)
            unvisited.remove(current)
            neighbors = [j for i, j in edges.select(current, '*')
                         if j in unvisited]
        if len(cycle) > len(thiscycle):
            cycle = thiscycle
    return cycle

def read_coordinates():
    coordinates = []

    with open('coords') as f:
        lines = f.readlines()

        for line in lines:
            x1, y1, x2, y2 = [int(i) for i in line.split(" ")]
            coordinates.append(Coord(x1, y1, x2, y2))

    return coordinates

def compute_alpha(pi, Zup, Zlbk, g1, g2):
    return pi*(Zup - Zlbk)/(g1**2+g2**2)

def run_model(coords, similarity):
    
    n = len(coords)

    # Costs for traveling salesman 1
    c1 = {
        (i, j): ceil(sqrt((coords[i].x1 - coords[j].x1) ** 2 + (coords[i].y1 - coords[j].y1) ** 2))
        for i in range(n)
        for j in range(i)
    }

    # Costs for traveling salesman 2
    c2 = {
        (i, j): ceil(sqrt((coords[i].x2 - coords[j].x2) ** 2 + (coords[i].y2 - coords[j].y2) ** 2))
        for i in range(n)
        for j in range(i)
    }

    m = gp.Model()

    # Variable indicating weather the edge is present (x1 = 1) or not (x1 = 0) in the cicle for traveling salesman 1
    x1 = m.addVars(c1.keys(), obj=c1, vtype=GRB.BINARY, name='e1') 

    # Variable indicating weather the edge is present (x2 = 1) or not (x2 = 0) in the cicle for traveling salesman 2
    x2 = m.addVars(c2.keys(), obj=c2, vtype=GRB.BINARY, name='e2')

    # Variable indicating weather the edge is present (z = 1) or not (z = 0) in both cicles
    z = m.addVars(c2.keys(), vtype=GRB.BINARY, name="z")

    for i, j in x1.keys():
        c1[j, i] = c1[i, j]
        c2[j, i] = c2[i, j]
        x1[j, i] = x1[i, j]
        x2[j, i] = x2[i, j]
        z[j, i] = z[i, j]

    # Constrainsts
    m.addConstrs(x1.sum(i, '*') == 2 for i in range(n)) 
    m.addConstrs(x2.sum(i, '*') == 2 for i in range(n))
    m.addConstr(sum(z[i, j] for i, j in c2.keys()) == 0)

    m._x1 = x1
    m._x2 = x2
    m.Params.LazyConstraints = 1
    m.setParam('TimeLimit', 60*30)
    m.setParam('LogToConsole', 0)

    lambda1 = 0.2
    lambda2 = 0.2

    # Start subgradient
    for k in range(9):

        # Objective function with penalty
        objective_func = gp.quicksum(
            x1[i, j]*c1[i, j] + x2[i, j]*c2[i, j]
            + lambda1*(x1[i, j] - z[i, j])
            + lambda2*(x2[i, j] - z[i, j])

            for i in range(n)
            for j in range(i)
        )

        m.setObjective(objective_func, GRB.MINIMIZE)
        m.optimize(subtourelim) #optmize model to get Zlb for the kth iteration

        if m.SolCount > 0:  
        
            # Get solutions for this iterationss
            x1k = m.getAttr('X', x1)
            x2k = m.getAttr('X', x2)
            zk = m.getAttr('X', z)
            Zlbk = m.ObjVal

            G = Graph(similarity, n, c1, c2, x1k, x2k, zk)

            Zup = G.compute_upper_bound_heuristic()

            g1 = 1 - sum(zk[i,j] - x1k[i,j] for i in range(n) for j in range(i))
            g2 = 1 - sum(zk[i,j] - x2k[i,j] for i in range(n) for j in range(i))

            print(g1)

            alpha = compute_alpha(1.5, Zup, Zlbk, g1, g2)

            # Update lagrangian multipliers with new alpha and g values
            lambda1 = max(0, lambda1 + alpha*g1)
            lambda2 = max(0, lambda2 + alpha*g2)    
            print(k)
            print(Zlbk)
            print(Zup)    

    if m.SolCount > 0:
        print_solution(m, x1, x2, c1.keys())

    return m


def print_solution(m, var_tsp_1, var_tsp_2, edges):
    m.Params.SolutionNumber = 0

    valsTsp1 = m.getAttr('X', var_tsp_1)
    valsTsp2 = m.getAttr('X', var_tsp_2)

    tour1 = subtour(valsTsp1)
    tour2 = subtour(valsTsp2)

    # draw_graph(valsTsp1, valsTsp2, edges)

    assert len(tour1) == n
    assert len(tour2) == n

    print('Optimal tour 1: %s' % str(tour1))
    print('Optimal tour 2: %s' % str(tour2))
    print('Optimal cost: %g' % m.ObjVal)


if __name__ == "__main__":
    N = [50]
    coords = read_coordinates()

    for n in N:
        for k in [0, n//2, n]:
            file = open('results/N{}K{}'.format(n, k), 'w')
            sys.stdout = file
            newCords = coords[0:n]

            model = run_model(newCords, k)


