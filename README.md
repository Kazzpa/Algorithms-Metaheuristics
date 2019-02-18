# Algorithms-Metaheuritics
**A project made for the college where we solve a problem using 3 different algorithms and we study how far do they get and which one gets the best solution**  
# Code
Its core implementation its [here](/Algorithms/src/Principal)
- Solution: its 1 array that show which doctor is the patient assigned, and an auxiliar array which shows how many patients does a doctor have assigned so we can check their limit easily.
- Population: its where most operands are made, it has a list of solutions and it contains the genetic and memetic main operands.  
Then we have the different implementations that use this core implementation we have:
- [Genetic](/Algorithms/src/Genetic/GeneticAlg.java)
- [Memetic](/Algorithms/src/Memetic/MemeticAlg.java)
- [Simulated Annealing](Algorithms/src/SimulatedAnnealing//SimulatedAnnealing.java)
  
 I use [Main.java](/Algorithms/src/Principal/Main.java) to test these different implementations,it has some static attributes at the start so you can choose which algorithms to execute, if you want a debug mode it also contains a generateData method so we initialize some patients and some doctors 

## Problem: 
A health insurance company wants to make an optimal allocation of its customers to the doctors of
general medicine available in a given city. You want to minimize the displacement that each
patient should make from home to the doctor's office assigned by the company. Recruitment
of a specific doctor to attend patients of the company has a certain fixed cost, which is
regardless of the number of patients assigned to you. It should be noted that each physician
you will have a maximum number of patients that you will be able to treat.  

The company requests that a tool be implemented that intelligently decides which patients will be treated.
doctors who must be hired from all available in the city. In addition you will need to decide the list of
patients who will be assigned to each contracted physician, taking into account that the company wishes to give you
coverage to all your patients minimizing the costs of hiring doctors.  
_Translated with www.DeepL.com/Translator_

## Problema:
Una compañía de seguros médicos desea realizar una asignación óptima de sus clientes a los médicos de
medicina general disponibles en una determinada ciudad. Se desea minimizar el desplazamiento que cada
paciente debería hacer desde su domicilio a la consulta del médico asignado por la compañía. La contratación
de un médico concreto para que atienda a pacientes de la compañía tiene un coste fijo determinado, que es
independiente del número de pacientes que le sean asignados. Hay que tener en cuenta que cada médico
tendrá un número máximo de pacientes a los que podrá atender.  

La  compañía  solicita   que  se  implemente   una   herramienta   que   decida   de  forma  inteligente   cuáles  son   los
médicos que  se deben contratar  de  todos  los disponibles  en  la  ciudad. Además  deberá decidir   la  lista  de
pacientes que serán asignados a cada médico contratado, teniendo en cuenta que la compañía desea darle
cobertura a todos sus pacientes minimizando los costes de contratación de los médicos.
