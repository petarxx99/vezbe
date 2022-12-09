package glavnipaket;

import engleski.NRNFunctional;
import engleski.NRNFunctionalWithStreams;

public class Main {

    public static void main(String[] args){
        NRNFunctionalWithStreams najveciRastuciNizFS = new NRNFunctionalWithStreams();
        BrziNacinZaNRN brziNacinZaNRN = new BrziNacinZaNRN();
        NRNFunctional nrnFunctional = new NRNFunctional();
        StariNacin stariNacin = new StariNacin();

        NajveciRastuciNizApp app = new NajveciRastuciNizApp(najveciRastuciNizFS);
        app.startApp();
    }
}
