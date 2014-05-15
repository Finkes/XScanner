program test5;
	read  x : int;
	print y : float;
	z : int;
begin
	while (x<4) begin
		for (z:=0; z<4; z:=z+1) x:=x+2;
		if x=4 then begin
			x:=z*(x+2);
			x:=x+10;
		end else y:=100;
	end;
end.