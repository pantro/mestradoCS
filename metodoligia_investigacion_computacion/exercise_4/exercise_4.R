# ----------------------
# Datos no pareados
#-----------------------
#Read file
data <- read.csv("~/Documentos/github/maestriaCS/metodoligia_investigacion_computacion/exercise_4/ex2.csv", stringsAsFactors = FALSE) 
#Seed
set.seed(1234)
#Install package
install.packages("BEST")
library(BEST)

#Separate data by people who have diabetes and people who do not have diabetes
si_diabetes <- data$bp[data$type=="Yes"]
no_diabetes <- data$bp[data$type=="No"]

#BEST
BESTout <- BESTmcmc(si_diabetes, no_diabetes, parallel=FALSE)
#Plot
plot(BESTout)

meanDiff <- (BESTout$mu1 - BESTout$mu2)
meanDiffGTzero <- mean(meanDiff > 0)
meanDiffGTzero

plot(BESTout, ROPE=c(-4,4))

# ----------------------
# Datos pareados
#-----------------------
#Read file
data2 <- read.csv("~/Documentos/github/maestriaCS/metodoligia_investigacion_computacion/exercise_4/ex2-paired.csv", stringsAsFactors = FALSE) 

#Seed
set.seed(1234)

#Install package
install.packages("devtools")
devtools::install_github("rasmusab/bayesian_first_aid")
library(BayesianFirstAid)

resultbayes <- bayes.t.test(data2$November, data2$August, paired = T)
resultbayes

plot(resultbayes)

#BEST
library(BEST)
resultBest <- BESTmcmc(data2$November-data2$August, parallel=FALSE)
resultBest

#Plot
plot(resultBest)

plot(resultBest, ROPE=c(-3.5,3.5))



