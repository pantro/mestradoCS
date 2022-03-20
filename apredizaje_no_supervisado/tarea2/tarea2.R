install.packages("mclust")
library(mclust)

data <- read.table("~/Documentos/github/maestriaCS/apredizaje_no_supervisado/tarea2/ex2-data.csv", stringsAsFactors = FALSE)

mod1 <- Mclust(iris[,1:4])
summary(mod1)

mod2 <- Mclust(iris[,1:4], G = 3)
summary(mod2, parameters = TRUE)