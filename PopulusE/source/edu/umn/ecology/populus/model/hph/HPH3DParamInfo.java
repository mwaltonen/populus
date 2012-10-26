package edu.umn.ecology.populus.model.hph;
import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;
import java.util.*;

public class HPH3DParamInfo extends APPD3DProtoParamInfo {
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.hph.Res" );
   
   public HPH3DParamInfo( double N0, double P0, double Q0, double l, double a1, double a2, double k1, double k2, int gens, boolean vsTime ) {
      discProc = new HPHProc( l, a1, a2, k1, k2 );
      this.gens = gens;
      initialConditions = new double[] {
         N0, P0, Q0
      };
      mainCaption = res.getString( "Discrete_Predator" );
      xCaption = res.getString( "Predator_2_Q_" );
      yCaption = res.getString( "Prey_N_" );
      zCaption = res.getString( "Predator_1_P_" );
   }
}
