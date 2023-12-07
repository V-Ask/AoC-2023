solutions <- polyroot(c(-9, 7, -1))
times <- "Time:        63789468"
distances <- "Distance:   411127420471035"
timestrings <- strsplit(times, " +")[[1]][-1]
distancestrings <- strsplit(distances, " +")[[1]][-1]

distance <- 411127420471035
time <- 63789468
roots <- Re(polyroot(c(- distance - 0.1, time, -1)))
from <- ceiling(roots[1])
to <- floor(roots[2])
inbetweeners <- from:to
output <- output * length(inbetweeners)

print(output)