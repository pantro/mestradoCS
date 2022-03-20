#Point 1
set.seed(1234)
data1 <- rnorm(30, 10, 5)
data2 <- rnorm(30, 12, 5)
data1
data2

#Point 2
sum <- 0
for (i in 1:50) {
  test=t.test(rnorm(30,10,5),rnorm(30,12,5))
  print(paste(i,":",round(test$p.value,3)))
  sum <- sum +test$p.value
}
media <- sum/50
print(media)

#Point 3
sum <- 0
for (i in 1:50) {
  test=t.test(rnorm(60,10,5),rnorm(60,12,5))
  print(paste(i,":",round(test$p.value,3)))
  sum <- sum +test$p.value
}
media <- sum/50
print(media)

#Point 4
sum <- 0
for (i in 1:50) {
  test=t.test(rnorm(30,10,10),rnorm(30,12,10))
  print(paste(i,":",round(test$p.value,3)))
  sum <- sum +test$p.value
}
media <- sum/50
print(media)

#Point 5
sum <- 0
for (i in 1:50) {
  test=t.test(rnorm(30,10,5),rnorm(30,15,5))
  print(paste(i,":",round(test$p.value,3)))
  sum <- sum +test$p.value
}
media <- sum/50
print(media)

#Point 6
#-------------------------------
#Point 7
#------------------------------------
#Point 2
sum <- 0
for (i in 1:50) {
  wtest=wilcox.test(rnorm(30,10,5),rnorm(30,12,5))
  print(paste(i,":",round(wtest$p.value,3)))
  sum <- sum +wtest$p.value
}
media <- sum/50
print(media)
#Point 3
sum <- 0
for (i in 1:50) {
  wtest=wilcox.test(rnorm(60,10,5),rnorm(60,12,5))
  print(paste(i,":",round(wtest$p.value,3)))
  sum <- sum + wtest$p.value
}
media <- sum/50
print(media)
#Point 4
sum <- 0
for (i in 1:50) {
  wtest=wilcox.test(rnorm(30,10,10),rnorm(30,12,10))
  print(paste(i,":",round(wtest$p.value,3)))
  sum <- sum + wtest$p.value
}
media <- sum/50
print(media)
#Point 5
sum <- 0
for (i in 1:50) {
  wtest=wilcox.test(rnorm(30,10,5),rnorm(30,15,5))
  print(paste(i,":",round(wtest$p.value,3)))
  sum <- sum + wtest$p.value
}
media <- sum/50
print(media)


#Read file
data <- read.csv("~/Documentos/github/maestriaCS/metodoligia_investigacion_computacion/exercise_3//ex2.csv", stringsAsFactors = FALSE) 
#Seed
set.seed(1234)

#Separate data by people who have diabetes and people who do not have diabetes
si_diabetes <- data$bp[data$type=="Yes"]
no_diabetes <- data$bp[data$type=="No"]

t.test(si_diabetes)
t.test(no_diabetes)

wilcox.test(si_diabetes,conf.int=T)
wilcox.test(no_diabetes,conf.int=T)

library(boot)
auxf <- function(dado,indice){
  return(mean(dado[indice]))
}
bb = boot(si_diabetes,R=5000, statistic=auxf)
boot.ci(bb,type="bca")

library(boot)
auxf <- function(dado,indice){
  return(mean(dado[indice]))
}
bb = boot(no_diabetes,R=5000, statistic=auxf)
boot.ci(bb,type="bca")

#--------------------
#- 3
#------------------
set.seed(1234)
t.test(si_diabetes, no_diabetes)

#--------------------
#- 4
#------------------
install.packages("effsize")
library(effsize)
cohen.d(si_diabetes, no_diabetes, na.rm = T, pooled = T)








