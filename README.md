# Java test AI library

## Intro notes

This library is a clone of an old-ish project that I did while learning about some AI concepts, and trying to implement them on my own.

The most interesting things are probably the different results you can get : markov-chains based string generations, ant population selection and colormap creation.

## Contents

### Core : under `src/com.gloaguen2u.common`

- Genetic Algorithms `genalgs` contains core methods to manipulate genes, as well as basis for gene selection and population management
- Neural Networks `neuralnets` contains algorithms to create and use a forward-propagation neural network. It DOES NOT contains constraints/backward-propagation methods.
- Markov Chains `markov` contains basis for implementing markov chains and using them.
- Kohonen maps `kohonens` contains a base element to manipulate a 2D kohonen map.

### Viewers : under `app`

Contains the different Javascript + HTML interfaces to view the results of the `colorsMap` and `markovStrings` projects. See the descriptons for their use.

### Ant population analysis : under `src/com.gloaguen2u.antfood`

A cool project to create and observe an ant population struggle to get food.

The idea (based on an article I read a while ago and I can't find now) is to have an ant (represented as two neurons) search for food, based on the direction of the food piece they are the closest to.

The ants are then selected and bred for the next generation.

An ouput is given as values, as well as a JSON representation fo all the ant positions through time.

### Color map generator : under `src/com.gloaguen2u.colorsMap`

A project to create a basic color map, linking colors together, using the Kohonen maps algorithm.

The idea is to input some colors and an output map will be created.

Use `app/colorsPrinteer.html` to see the result.

### Markov strings generator : under `src/com.gloaguen2u.markovStrings`

A project to create some markov strings based on some input text.

Use `app/markovStrings.html` to see the result.

### Find the formula : under `src/com.gloaguen2u.findFormula`

A project that, from a set of input numbers, find the fomula to get an output number.

Based on genetic algorithms.

### Shop town problem : under `src/com.gloaguen2u.shopTown`

A project to solve the ShopTown project we were assigned at some point during the class. It was a good way to test the genetic algorithms generality.

## License

This is MIT, and if you want to use the code go ahead. Just be aware that there's probably better out there.