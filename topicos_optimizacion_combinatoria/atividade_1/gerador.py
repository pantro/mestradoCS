from random import randint as rand

class Gerador ( object ):

    def gen (self, J):
        F = rand ( J, 2*J )
        L = rand ( 5, 10 )
        M = rand ( 5, 10 )
        P = rand ( 5, 10 )
        D =   [ [ rand(10,20 ) for j in range(J) ] for p in range(P) ]
        r = [ [ [ rand( 1, 5 ) for l in range(L) ] for m in range(P) ] for p in range(M) ]
        R =   [ [ rand(800,1000) for f in range(F) ] for m in range(M) ]
        C =   [ [ rand(80,100) for l in range(L) ] for f in range(F) ]
        C_p = [ [ [ rand(10,100) for l in range(L) ] for f in range(F) ] for p in range(P) ]
        C_t = [ [ [ rand(10,20 ) for j in range(J) ] for f in range(F) ] for p in range(P) ]

        return (J, F, L, M, P, D, r, R, C, C_p, C_t)
