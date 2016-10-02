# CraigLanguage

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
