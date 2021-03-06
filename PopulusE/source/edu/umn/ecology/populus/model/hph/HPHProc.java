/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.hph;
import edu.umn.ecology.populus.math.*;

public class HPHProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
	private double l, a1, a2, k1, k2;

	@Override
	public void v( long t, double[] y ) {
		double f1, f2, N, P, Q;
		N = y[0];
		P = y[1];
		Q = y[2];
		if( k1 != 0 ) {
			f1 = Math.exp( Math.log( 1 + a1 * P / k1 ) * -k1 );
		}
		else {
			f1 = 0;
		}
		if( k2 != 0 ) {
			f2 = Math.exp( Math.log( 1 + a2 * Q / k2 ) * -k2 );
		}
		else {
			f2 = 0;
		}
		y[0] = l * N * f1;
		y[1] = N * ( 1 - f1 ) * f2;
		y[2] = N * ( 1 - f1 ) * ( 1 - f2 );
	}

	public HPHProc( double l, double a1, double a2, double k1, double k2 ) {
		this.l = l;
		this.a1 = a1;
		this.a2 = a2;
		this.k1 = k1;
		this.k2 = k2;
		this.numVariables = 3;
	}
} /*pascal code for "Host, Parasite, Hyperparasitoid"
 procedure V(var Pm:ParamArray;t:longint;var y_); far;
 var
 l,a1,a2,k1,k2,
 P,N,Q,f1,f2    : extended;
 y              : extendedArray absolute y_;
 begin
 l  := Pm[lp];
 a1 := Pm[a1p];
 a2 := Pm[a2p];
 k1 := Pm[k1p];
 k2 := Pm[k2p];

 N := y[Ny];
 P := y[Py];
 Q := y[Qy];

 if k1<>0 then
 f1 := exp(ln(1+a1*P/k1)*-k1)
 else
 f1 := 0;

 if k2<>0 then
 f2 := exp(ln(1+a2*Q/k2)*-k2)
 else
 f2 := 0;

 y[Ny] := l*N*f1;
 y[Py] := N*(1-f1)*f2;
 y[Qy] := N*(1-f1)*(1-f2);
 end;
 */

