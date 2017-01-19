# SemiColon Programming Language (SPL)

Compiler creation in progress https://github.com/Craigspaz/SPL-Compiler

Intended for serverside scripting for websites.

## Hello World

```
Print("Hello World");
```

## Print Numbers 1-10 while loop

```
var x = 1;
while(x <= 10);
	Print(x);
endloop;
```

## Print Numbers 1-10 for loop

```
for(var y = 1; y <= 10; y = y + 1);
	Print(y);
endloop;
```
## Sample Function use

```
function testFunction(var value, var other);
	Print(value);
	Print(other);
endfunc;

testFunction(5,"Pie");
```

# Pointers

## Get memory address example

The code below sets y to the memory address of x
```
	var x = 10;
	var y = @x;
```

## Dereference operator example

The code below sets z to the value y points to
```
	var x = 10;
	var y = @x;
	var z = ~y;
```

## Set a value at the address a pointer points
The code below sets x to the value 5
```
	var x = 10;
	var y = @x;
	~y = 5;
```

## Example Comment
```
//This is a comment. A semicolon is not required at the end of this comment because it is on it's own line
//Any line that starts with "//" is ignored
var x = 5; //This is also a comment but notice the semicolon at the end of this comment;

//The following line prints out the value of x
Print(x);
```
## Include Example
Content of testInclude.cr
```
function doesNotExistAnywhere();
	Print("This is a function");
endfunc;
```

Content of test.cr
```
include "./testFolder/testInclude.cr";
doesNotExistAnywhere();
```
If test.cr is executed the following is the result
```
This is a function
```

## Arrays
```
	var x = {};
	append(x,"Appends this string to x");
```

```
	var y = {6, "Hello", 2, 338, 29};
	var x = y[1];
	y[1] = 3;
	Print(x); // This prints out "Hello";
	Print(y[1]); // This prints out 3;
```
