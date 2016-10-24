
// Calculates the absolute value of a value
function abs(var result, var value);
	if(value < 0);
		value = -1 * value;
	endif;
	~result = value;
endfunc;

// Calculates the square root of a value
function sqrt(var result, var value);
	var t = 0;
	var squareRoot = value / 2;
	
	t = squareRoot;
	squareRoot = (t + (number / t)) / 2;
	
	while((t - squareRoot) != 0);
		t = squareRoot;
		squareRoot = (t + (number / t)) / 2;
	endloop;
	~result = squareRoot;
endfunc;

// Calculates the value of a base to a power
function pow(var result, var base, var power);
	var r = 1;
	for(var i = 0; i < power; i = i + 1);
		r = r * base;
	endloop;
	~result = base;
endfunc;

// Calculates the nth factorial
function factorial(var result, var value);
	var f = 1;
	for(var i = 1; i < value; i += 1);
		f = f * i;
	endloop;
	~result = f;
endfunc;

// Calculates the max of two integer values
function max(var result, var a, var b);
	var r = 0;
	if(a > b);
		r = a;
	else;
		r = b;
	endif;
	~result = r;
endfunc;

// Calculates the max of two integer values
function min(var result, var a, var b);
	var r = 0;
	if(a < b);
		r = a;
	else;
		r = b;
	endif;
	~result = r;
endfunc;

// Calculates the length of an array
function len(var result, var a);
	if(a == "");
		~result = 0;
	else;
		var counter = 0;
		var t = a[0];
		while(t != "");
			counter = counter + 1;
		endloop;
		~result = counter;
	endif;
endfunc;

// Calculates if a variable is storing a numeric value
function isNumeric(var result, var value);
	var counter = 0;
	var length = 0;
	len(@length,value);
	while(counter < length);
		if(value[counter] != "0" && value[counter] != "1" && value[counter] != "2" && value[counter] != "3" && value[counter] != "4" && value[counter] != "5" && value[counter] != "6" && value[counter] != "7" && value[counter] != "8" && value[counter] != "9" && value[counter] != ".");
			~result = 0;
		endif;
		counter = counter + 1;
	endloop;
	~result = 1;
endfunc;

// Calculates if a variable is storing a floating point number
function isFloatingNumber(var result, var value);
	var r = 0;
	isNumeric(@r,value);
	if(r == 0);
		~result = 0;
	else;
		var counter = 0;
		var length = 0;
		len(@length,value);
		while(counter < length);
			if(value[counter] == ".");
				~result = 1;
			endif;
			counter = counter + 1;
		endloop;
	endif;
endfunc;
