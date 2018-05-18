Ficheiro auxiliar para criar experiments:

Runs: Numero de seeds diferentes que o algoritmo vai usar para iniciar os pesos

Population size: Tamanho da população em estudo

Max generations: Numero maximo de iterações.

Snake type: é selecionado pela combobox;


Segue, um exemplo com todas as possibilidades

Runs: 10

Population size: 100

Max generations: 1000

//--------------------------------

Selection: tournament, roullette

Tournament size: 2, 4, 10

//--------------------------------

Recombination: one_cut, two_cuts, uniform

Recombination probability: 0.7, 0.8, 0.9

//--------------------------------

Mutation: uniform_distribution, uniform_distribution_limit, gaussian

Mutation probability: 0.7
Mutation limit: 0.5

//--------------------------------


//--------------------------------

Problem file: ./DataSets/DataSet1.txt

//--------------------------------

Statistic: BestIndividual
Statistic: BestAverage
