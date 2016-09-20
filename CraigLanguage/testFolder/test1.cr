var x = 10;
var y = 25;
var sum = (x + y) * (x + y);
var z = "Hello";
sum = sum + x;
sum = sum % 10;
Print("Hello World");

function testFunction();
	Print("This is a function");
endfunc;

function testFunction2(var value, var other);
	Print(value);
	testFunction();
	Print(other);
endfunc;

testFunction2(10,5);

if(x == 10 || false);
	Print("or works");
endif;
if((x == 11 || y == 25) && x % 2 == 0);
	Print("And and or Works");
endif;

if(x == 10);
	if(y == 25);
		Print("Nesting Works");
		if(x == 15);
			Print("Epic Nesting");
		else if(y == 25);
			Print("Super Epic Testing");
			if(x % 2 == 0);
				Print("X is EVEN");
			endif;
			for(var i = 0, i <= 8, i = i + 1);
				if(i % 2 == 0);
					Print(i);
				endif;
			endloop;
		endif;
	endif;
	Print("Test");
endif;

if(x == 20);
	Print("Test2");
else if(y == 25);
	Print("Test3");
endif;

if(x == 10);
	Print("Test4");
else if(y == 25);
	Print("Test5");
else if(x == 200);
	Print("You can't see me");
else;
	Print("Test6");
endif;

testFunction();

while(x < 15);
	Print(x);
	x = x + 1;
endloop;

if(x == 15);
	Print("X is really 15");
endif;