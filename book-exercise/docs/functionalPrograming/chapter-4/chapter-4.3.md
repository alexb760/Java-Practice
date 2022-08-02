# Composing a Huge Number of Functions

In Chapter 2, you saw that you’ll overflow the stack if you try to compose a huge number of
functions. The reason is the same as for recursion: because composing functions results in methods
calling methods.

Having to compose more than 7,000 functions may be something you don’t expect to do soon. On the
other hand, there’s no reason not to make it possible. If it’s possible, someone will eventually
find something useful to do with it. And if it’s not useful, someone will certainly find something
fun to do with it.

```
static<T> Function<T, T> composeAll(List<Function<T, T>>list){
    return foldRight(list,identity(),x->y->x.compose(y));
    }
```

To test this method, you can statically import all the methods from your Collection-Utilities
class (developed in Chapter 3) and write the following:

    Function<Integer, Integer> add=y->y+1;
        System.out.println(composeAll(map(range(0,500),x->add)).apply(0));

If you don’t feel comfortable with this kind of code, it’s equivalent to, but much more readable
than, this:

```
List<Function<Integer, Integer>>list=new ArrayList<>();
    for(int i=0;i< 500;i++){
    list.add(x->x+1);
    }
```

int result = composeAll(list).apply(0); System.out.println(result);

Running this code displays 500, as it’s the result of composing 500 functions incrementing their
argument by 1. What happens if you replace 500 with 10,000? You’ll get a StackOverflowException. The
reason should be obvious.

### Fixing:

The solution to this problem is simple. Instead of composing the functions by nesting them, you have
to compose their results, always staying at the higher level. This means that between each call to a
function, you’ll return to the original caller. If this isn’t clear, imagine the imperative way to
do this:
```
 T y = identity;
 for (Function<T, T> f : list) { y = f.apply(y); }
```
Here, identity means the identity element of the given function. This isn’t composing functions, but
composing function applications. At the end of the loop, you’ll get a T and not a Function<T, T>.
But this is easy to fix. You create a function from T to T, which has the following implementation:
