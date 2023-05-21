package ppgit.hlamodule;

import java.io.File;
import java.net.MalformedURLException;

import hla.rti1516e.CallbackModel;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateAlreadyExecutionMember;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.InconsistentFDD;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;

public class HlaInterfaceImpl extends NullFederateAmbassador {
	
	private RTIambassador ambassador;
	
	public void run(String fedName) throws RTIinternalError {
		RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
		ambassador = rtiFactory.getRtiAmbassador();
		
		try {
			ambassador.connect(this, CallbackModel.HLA_IMMEDIATE, "192.168.0.6");
		} catch (ConnectionFailed |
				InvalidLocalSettingsDesignator |
				UnsupportedCallbackModel |
				AlreadyConnected |
				CallNotAllowedFromWithinCallback |
				RTIinternalError e) {
			throw new RTIinternalError("Erro na conexão com o RTI", e);
		}
		
		File fom = new File("PerformanceEvaluation.xml");
				
		try {
			ambassador.createFederationExecution("PPGIT", fom.toURI().toURL());
		} catch (InconsistentFDD |
				ErrorReadingFDD |
				CouldNotOpenFDD |
				FederationExecutionAlreadyExists |
				NotConnected |
				RTIinternalError |
				MalformedURLException e) {
			throw new RTIinternalError("Erro na criação da Federação", e);
		}
		
		try {
			ambassador.joinFederationExecution(fedName, "PPGIT");
		} catch (CouldNotCreateLogicalTimeFactory |
				FederationExecutionDoesNotExist |
				SaveInProgress |
				RestoreInProgress |
				FederateAlreadyExecutionMember |
				NotConnected |
				CallNotAllowedFromWithinCallback |
				RTIinternalError e) {
			throw new RTIinternalError("Erro na conexão com a Federação", e);
		}
	}

}
