
function abs(var result, var value);
	if(value < 0);
		value = -1 * value;
	endif;
	~result = value;
endfunc;

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

function pow(var result, var base, var power);
	var r = 1;
	for(var i = 0; i < power; i = i + 1);
		r *= base;
	endloop;
	~result = base;
endfunc;