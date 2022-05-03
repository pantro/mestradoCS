import gurobipy as gp
from gurobipy import GRB
from gerador import Gerador

class Solver:
    def solve(self, J, F, L, M, P, D, r, R, C, C_p, C_t):
        model = gp.Model("Atividade 1")
        # Variables declaration
        x = [ [ [ model.addVar(lb=0.0, ub=GRB.INFINITY, obj=C_p[p][f][l] , vtype=GRB.INTEGER, name=f"x[{p}][{f}][{l}])") for l in range(L) ] for f in range(F) ] for p in range(P) ]
        y = [ [ [ model.addVar(lb=0.0, ub=GRB.INFINITY, obj=C_t[p][f][j] , vtype=GRB.INTEGER, name=f"y[{p}][{f}][{j}])") for j in range(J) ] for f in range(F) ] for p in range(P) ]

        model.update()
        model.ModelSense = GRB.MAXIMIZE

        # RESTRICTIONS
        # Demmand constrains
        model.addConstrs(
            ( sum([y[p][f][j] for f in range(F)]) >= D[p][j] for j in range(J) for p in range(P)),
            name="demmand"
        )
        # Produced equals transported constrains
        model.addConstrs(
            (sum(x[p][f]) - sum(y[p][f]) == 0 for f in range(F) for p in range(P)),
            name="prod_equals_transp"
        )
        # Material availability constrains
        model.addConstrs(
            (sum([x[p][f][l] for p in range(P) for l in range(L)]) <= R[m][f] for f in range(F) for m in range(M)),
            name="material"
        )
        # Capacity constrains
        model.addConstrs(
            (sum([x[p][f][l] for p in range(P)]) <= C[f][l] for f in range(F) for l in range(L)),
            name="capacity"
        )

        model.optimize()

        with open("res.csv", "a") as res:
            res.write(f"Instancia com J =  , {J}\n")
            res.write(f"X variables        , {P*F*L}\n")
            res.write(f"Y variables        , {P*F*J}\n")
            res.write(f"Demmand            , {J*P}\n")
            res.write(f"Prod_equals_transp , {F*P}\n")
            res.write(f"Meterial           , {F*M}\n")
            res.write(f"Capacity           , {F*L}\n")
            res.write(f"Objecttive func    , {model.objVal}\n\n")
        model.write(f"instancia {J}.json")
        model.dispose()

if __name__ == "__main__":
    g = Gerador()
    s = Solver()
    for i in range(1,11):
        J, F, L, M, P, D, r, R, C, C_p, C_t = g.gen(i*10)
        print("Instância = ",J, F, L, M, P)
        s.solve(J, F, L, M, P, D, r, R, C, C_p, C_t)
