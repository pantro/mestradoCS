#Read file
data <- read.csv("~/Documentos/mestradoCS/metodoligia_investigacion_computacion/exercise_2/ex2-paired.csv", stringsAsFactors = FALSE) 

set.seed(10)

#'-------------------------
#'teste t
#'-------------------------
test <- t.test(data$November, data$August, paired = TRUE) # teste
print(test)
#Paired t-test
#
#data:  data$November and data$August
#t = 2.3089, df = 12, p-value = 0.03956
#alternative hypothesis: true difference in means is not equal to 0
#95 percent confidence interval:
#  0.2760652 9.5239348
#sample estimates:
#  mean of the differences 
#4.9 

boxplot(data$November,data$August,names=c("November","August"))#Show the diagrams
medias <- c(mean(data$November),mean(data$August))#Show the mean by a point
points(medias,pch=18,col="red")#Highlight the mean of a color

#'-------------------------
#'Wilcoxon rank sum
#'-------------------------
wilcox.test(data$November, data$August, paired = TRUE)
#Wilcoxon signed rank exact test
#
#data:  data$November and data$August
#V = 75, p-value = 0.03979
#alternative hypothesis: true location shift is not equal to 0

