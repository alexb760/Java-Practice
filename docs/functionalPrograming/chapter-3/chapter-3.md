# Abstracting Control Structures.
Through this chapter we saw how we can transform our code from imperative way to a functional
by applying simple interfaces.

![interface design](interces.png)

by using this design and implementing a ``Effect<T>`` interface which as its name indicates
will apply and effect over the main function we could use a ```Consumer``` from java libraries
but this is up to any developer in this case is for learning proposal.

###Hint

First, you’ll need an interface with a single method to represent an effect. 
Second, because the emailChecker function returns a Result, the validate method could 
return this Result. In such a case, you’d no longer need the validate method. Third, 
you’ll need to "bind" an effect to the Result. But because the result may be a success 
or a failure, it would be better to bind two effects and let the Result class choose which one to apply.

As a result we can build fluent and functional methods with pure functions where no side effects.
just the side effect we want.

![code](codeResult.png)
