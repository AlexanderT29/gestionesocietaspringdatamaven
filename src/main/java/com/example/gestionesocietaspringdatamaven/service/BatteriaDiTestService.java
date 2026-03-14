package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Progetto;
import com.example.gestionesocietaspringdatamaven.model.Societa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatteriaDiTestService {

    @Autowired
    private SocietaService societaService;

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private ProgettoService progettoService;

    public void testInserisceSocieta(){
        System.out.println("testInserisciSocieta .... inizio");
        Long nowInMillisecondi = LocalDate.now().toEpochDay();

        Societa societa = new Societa("Societa" + nowInMillisecondi, "via " + nowInMillisecondi, LocalDate.now(), LocalDate.of(2050, 1, 1));
        if(societa.getId() != null){
            throw new RuntimeException("testInserisciSocieta failed: transient object con id valorizzato");
        }
        societaService.inserisciSocieta(societa);

        if(societa.getId() == null || societa.getId() < 1){
            throw new RuntimeException("testInserisciSocieta failed: inserimento fallito");
        }
        System.out.println("testInserisciSocieta .... OK\n");
    }

    public void testFindByExample(){
        System.out.println("testFindByExample ... inizio");
        Societa societa1 = new Societa("VGroup", "via lagopatria ì42", LocalDate.of(2021, 11, 20), null);
        societaService.inserisciSocieta(societa1);

        Societa societa2 = new Societa("Gigi Group", "via gomma 43", LocalDate.of(2017, 10, 19), LocalDate.of(2020, 5, 20));
        societaService.inserisciSocieta(societa2);

        Societa societa3 = new Societa("Gattini Group", "via fusa 29", LocalDate.of(2000, 10, 5), LocalDate.of(2024, 4, 12));
        societaService.inserisciSocieta(societa3);

        Societa example = new Societa("Group", null, null, null);
        List<Societa> result = societaService.findByExample(example);

        if(result.size() != 3){
            throw new RuntimeException("testFindByExample failed: societa non trovate");
        }

        System.out.println("testFindByExample ... OK\n");
    }

    public void testInserisciDipendente(){
        System.out.println("testInserisciDipendente ... INIZIO");

        Societa techSolutions = new Societa("Tech Solutions S.R.L.", "Via Roma 10, Milano", LocalDate.of(2010, 5, 12), null);
        societaService.inserisciSocieta(techSolutions);

        Dipendente dipendente1 = new Dipendente("Mario", "Rossi", LocalDate.of(2015, 1, 10), 35000, techSolutions);
        if(dipendente1.getId() != null){
            throw new RuntimeException("testInserisciDipendente faile: transient object con id valorizzato");
        }
        dipendenteService.inserisciDipendente(dipendente1);
        if(dipendente1.getId() == null || dipendente1.getId() < 1){
            throw new RuntimeException("testInserisciDipendente failed: inserimento fallito");
        }

        System.out.println("testInserisciDipendente ... OK\n");
    }

    public void testRimuoviSocietaConEccezione(){
        System.out.println("testRimuoviSocietaConEccezione ... INIZIO");

        Societa greenPower = new Societa("Green Power Solution", "Viale delle Scienze 8, Bologna", LocalDate.of(2020, 1, 1), null);
        societaService.inserisciSocieta(greenPower);

        dipendenteService.inserisciDipendente(new Dipendente("Alessandro", "Moretti", LocalDate.of(2020, 2, 1), 55000, greenPower));

        try {
            societaService.rimuovi(greenPower.getId());
        } catch (Exception e) {
            System.out.println("testRimuoviSocietaConEccezione ... OK Exception trovata");
        }

        System.out.println("testRimuoviSocietaConEccezione ... FINE\n");
    }


    public void testInserisciProgetto(){
        System.out.println("testInserisciProgetto ... INIZIO");

        Progetto nuovoProgetto = new Progetto("Sviluppo Portale Cloud", "Clienti Internazionali S.P.A.", 18);
        if(nuovoProgetto.getId() != null){
            throw new RuntimeException("testInserisciProgetto FAILED: progetto è già instanziato nel db");
        }
        progettoService.inserisciProgetto(nuovoProgetto);
        if(nuovoProgetto.getId() == null || nuovoProgetto.getId() < 1){
            throw new RuntimeException("testInserisciProgetto FAILED: il progetto non è stato inserito!");
        }

        System.out.println("testInserisciProgetto ... OK\n");
    }

    public void testCollegaDipendenteAProgetto(){
        System.out.println("testCollegaDipendenteAProgetto ... INIZIO");
        Societa societa1 = new Societa("Fiori Belli S.R.L.", "Via dei tulipani 10", LocalDate.of(2002, 3, 21), LocalDate.of(2029, 11, 10));
        societaService.inserisciSocieta(societa1);

        Dipendente dip = new Dipendente("Paolo", "Neri", LocalDate.now(), 30000, societa1);
        dipendenteService.inserisciDipendente(dip);

        List<Progetto> progetti = new ArrayList<>();

        Progetto proj1 = new Progetto("Sviluppo AI", "Google", 24);
        progettoService.inserisciProgetto(proj1);

        Progetto proj2 = new Progetto("Sviluppo Fiorellini", "Fioraio", 18);
        progettoService.inserisciProgetto(proj2);

        progetti.add(proj1);
        progetti.add(proj2);

        dipendenteService.collegaDipendenteAProgetti(dip, progetti);


        //dip = dipendenteService.caricaSingoloDipendente(dip.getId());

        if(dip.getProgettoSet().isEmpty()){
            throw new RuntimeException("testCollegaDipendenteAProgetto FAILED: non è stato collegato il progetto al dipendente");
        }
        System.out.println("testCollegaDipendenteAProgetto ... OK\n");

    }

    public void testCollegaProgettoADipendenti(){
        System.out.println("testCollegaProgettoADipendenti ... INIZIO");

        Societa soc = new Societa("Team Sviluppo", "Via dei Test 1", LocalDate.now(), null);
        societaService.inserisciSocieta(soc);

        Dipendente d1 = new Dipendente("Marco", "Ferruzzi", LocalDate.now(), 30000, soc);
        Dipendente d2 = new Dipendente("Anna", "panna", LocalDate.now(), 35000, soc);
        dipendenteService.inserisciDipendente(d1);
        dipendenteService.inserisciDipendente(d2);

        Progetto proj = new Progetto("Nuovo Gestionale", "Cliente X", 10);
        progettoService.inserisciProgetto(proj);

        List<Dipendente> dipendentiDaCollegare = new ArrayList<>();
        dipendentiDaCollegare.add(d1);
        dipendentiDaCollegare.add(d2);

        progettoService.collegaProgettoADipendenti(proj, dipendentiDaCollegare);


        Progetto projReloaded = progettoService.caricaSingoloProgetto(proj.getId());

        if (projReloaded.getDipendenteSet().size() != 2) {
            throw new RuntimeException("testCollegaProgettoADipendenti FAILED: Numero dipendenti collegati errato");
        }

        System.out.println("testCollegaProgettoADipendenti ... PASSED\n");
    }

    public void testGetListaDipendentiDaSocieta() {
        System.out.println("testGetListaDipendentiDaSocieta ... INIZIO");

        Societa soc = new Societa("Innovazione Digitale", "Via delle StartUp 5", LocalDate.of(2021, 1, 1), null);
        societaService.inserisciSocieta(soc);

        dipendenteService.inserisciDipendente(new Dipendente("Marco", "Rossi", LocalDate.now(), 25000, soc));
        dipendenteService.inserisciDipendente(new Dipendente("Sara", "Verdi", LocalDate.now(), 28000, soc));
        dipendenteService.inserisciDipendente(new Dipendente("Luca", "Neri", LocalDate.now(), 22000, soc));

        List<Dipendente> dipendentiRecuperati = societaService.getListaDipendentiDaSocieta(soc.getId());

        if (dipendentiRecuperati == null) {
            throw new RuntimeException("testGetListaDipendentiDaSocieta FAILED: La lista restituita è null!");
        }

        if (dipendentiRecuperati.size() != 3) {
            throw new RuntimeException("testGetListaDipendentiDaSocieta FAILED: Mi aspettavo 3 dipendenti, ne ho trovati " + dipendentiRecuperati.size());
        }

        if (!dipendentiRecuperati.get(0).getNome().equals("Marco")) {
            System.out.println("Nota: L'ordine potrebbe variare, ma Marco dovrebbe essere presente.");
        }

        System.out.println("testGetListaDipendentiDaSocieta ... PASSED con " + dipendentiRecuperati.size() + " dipendenti.\n");
    }




    public void testgetListaSocietaConProgettiDurataMaggioreUnAnno() {
        System.out.println("getListaSocietaConProgettiDurataMaggioreUnAnno ... INIZIO");

        Societa socTarget = new Societa("Azienda Progetti Lunghi S.P.A.", "Via Milano 1", LocalDate.now(), null);
        societaService.inserisciSocieta(socTarget);

        Societa socOut = new Societa("Azienda Progetti Corti S.R.L.", "Via Torino 2", LocalDate.now(), null);
        societaService.inserisciSocieta(socOut);

        Progetto proglungo = new Progetto("Sviluppo Infrastruttura", "Stato", 24);
        progettoService.inserisciProgetto(proglungo);

        Progetto progCorto = new Progetto("Sito Web", "Negozio", 3);
        progettoService.inserisciProgetto(progCorto);

        Dipendente d1 = new Dipendente("Luigi", "Bianchi", LocalDate.now(), 40000, socTarget);
        dipendenteService.inserisciDipendente(d1);

        Dipendente d2 = new Dipendente("Mario", "Verdi", LocalDate.now(), 30000, socOut);
        dipendenteService.inserisciDipendente(d2);

        dipendenteService.collegaDipendenteAProgetto(d1, proglungo);
        dipendenteService.collegaDipendenteAProgetto(d2, progCorto);

        List<Societa> risultati = societaService.getListaSocietaConProgettiDurataMaggioreUnAnno();

        if (risultati == null || risultati.isEmpty()) {
            throw new RuntimeException("test FAILED: Nessun risultato trovato!");
        }

        boolean trovata = risultati.stream()
                .anyMatch(s -> s.getId().equals(socTarget.getId()));

        if (!trovata) {
            throw new RuntimeException("test FAILED: La società target non è presente!");
        }

        trovata = risultati.stream()
                .anyMatch(s -> s.getId().equals(socOut.getId()));

        if (trovata) {
            throw new RuntimeException("test FAILED: Trovata società sbagliata (progetti corti)!");
        }

        System.out.println("getListaSocietaConProgettiDurataMaggioreUnAnno ... OK\n");
    }

    public void testProgettiConRalAltaEJPQL() {
        System.out.println("testProgettiConRalAltaEJPQL ... INIZIO");

        Societa soc = new Societa("Test Nativo", "Via SQL", LocalDate.now(), null);
        societaService.inserisciSocieta(soc);

        Progetto p1 = new Progetto("Sviluppo Nativo", "Cliente Nativo", 12);
        progettoService.inserisciProgetto(p1);

        Dipendente d1 = new Dipendente("Mario", "SQL", LocalDate.now(), 40000, soc);
        dipendenteService.inserisciDipendente(d1);

        dipendenteService.collegaDipendenteAProgetto(d1, p1);

        List<Progetto> risultati = progettoService.findDistinctByDipendenteSet_redditoAnnuoLordoGreaterThan(30000);

        List<Progetto> risultatiConQNativa = progettoService.getListaDipendentiConRALMAggioreDi(30000);

        boolean trovato = risultati.stream().anyMatch(p -> p.getId().equals(p1.getId()));

        boolean trovatoNativa = risultatiConQNativa.stream().anyMatch(p -> p.getId().equals(p1.getId()));

        if (!trovato) {
            throw new RuntimeException("test FAILED: Query jpql non ha restituito il progetto atteso!");
        }

        if (!trovatoNativa) {
            throw new RuntimeException("test FAILED: Query nativa non ha restituito il progetto atteso!");
        }

        System.out.println("testProgettiConRalAltaEJPQL ... OK\n");
    }

    public void testCercaDipendentePiuAnzianoNativaESpring() {
        System.out.println("testCercaDipendentePiuAnzianoNativaESpring ... INIZIO");

        Societa socStorica = new Societa("Antica Manifattura", "Via Vecchia 1", LocalDate.of(1980, 1, 1), null);
        societaService.inserisciSocieta(socStorica);

        Societa socModerna = new Societa("Startup 2000", "Via Nuova 100", LocalDate.of(2010, 5, 20), null);
        societaService.inserisciSocieta(socModerna);

        Progetto pLungo = new Progetto("Restyling Storico", "Cliente Heritage", 8);
        progettoService.inserisciProgetto(pLungo);

        Dipendente anziano = new Dipendente("Giuseppe", "Verdi", LocalDate.of(2000, 1, 1), 45000, socStorica);
        dipendenteService.inserisciDipendente(anziano);
        dipendenteService.collegaDipendenteAProgetto(anziano, pLungo);


        Dipendente giovane = new Dipendente("Mario", "Rossi", LocalDate.of(2015, 6, 15), 30000, socStorica);
        dipendenteService.inserisciDipendente(giovane);
        dipendenteService.collegaDipendenteAProgetto(giovane, pLungo);


        Dipendente risultatoSpring = dipendenteService.findAnzianoInSocietaStoricheSuProgettiLunghiSpring(LocalDate.of(1990, 1, 1), 6);
        Dipendente risultatoNativa = dipendenteService.findAnzianoInSocietaStoricheSuProgettiLunghi(LocalDate.of(1990, 1, 1), 6);

        if (risultatoSpring == null || risultatoNativa == null) {
            throw new RuntimeException("test FAILED: Nessun dipendente trovato!");
        }
        if (!risultatoSpring.getId().equals(anziano.getId()) || !risultatoNativa.getId().equals(anziano.getId())) {
            throw new RuntimeException("test FAILED: Il dipendente restituito non è il più anziano (assunto per primo)!");
        }

        System.out.println("testCercaDipendentePiuAnzianoNativaESpring ... OK");

    }


    public void testProgettiAnomali() {
        System.out.println("testProgettiAnomali ... INIZIO");

        Societa socChiusa = new Societa("Fallita S.R.L.", "Via del Tramonto 1", LocalDate.of(2015, 1, 1), LocalDate.of(2023, 1, 1));
        societaService.inserisciSocieta(socChiusa);

        Societa socAttiva = new Societa("Viva S.P.A.", "Via dell'Alba 2", LocalDate.of(2020, 1, 1), null);
        societaService.inserisciSocieta(socAttiva);

        Progetto progAnomalo = new Progetto("Progetto Fantasma", "Cliente X", 12);
        progettoService.inserisciProgetto(progAnomalo);

        Progetto progRegolare = new Progetto("Progetto Sano", "Cliente Y", 24);
        progettoService.inserisciProgetto(progRegolare);

        Dipendente dipSfortunato = new Dipendente("Aldo", "Baglio", LocalDate.of(2018, 1, 1), 25000, socChiusa);
        dipendenteService.inserisciDipendente(dipSfortunato);

        Dipendente dipFortunato = new Dipendente("Giovanni", "Storti", LocalDate.of(2021, 1, 1), 30000, socAttiva);
        dipendenteService.inserisciDipendente(dipFortunato);

        dipendenteService.collegaDipendenteAProgettoNoCheck(dipSfortunato, progAnomalo);
        dipendenteService.collegaDipendenteAProgettoNoCheck(dipFortunato, progRegolare);

        List<Progetto> risultati = progettoService.cercaProgettiAnomali();

        if (risultati == null || risultati.isEmpty()) {
            throw new RuntimeException("test FAILED: Nessun progetto anomalo trovato!");
        }

        boolean trovatoAnomalo = risultati.stream().anyMatch(p -> p.getId().equals(progAnomalo.getId()));
        boolean trovatoRegolare = risultati.stream().anyMatch(p -> p.getId().equals(progRegolare.getId()));

        if (!trovatoAnomalo) {
            throw new RuntimeException("test FAILED: Il progetto anomalo non è stato rilevato!");
        }

        if (trovatoRegolare) {
            throw new RuntimeException("test FAILED: Un progetto regolare è finito nella lista degli anomali!");
        }

        System.out.println("testProgettiAnomali ... OK");
    }

    public void testSocietaAnomale() {
        System.out.println("testSocietaAnomale ... INIZIO");

        Societa socAnomala = new Societa("Time Travel S.R.L.", "Via del Futuro 1", LocalDate.of(2020, 1, 1), null);
        societaService.inserisciSocieta(socAnomala);

        Societa socRegolare = new Societa("Azienda Normale S.P.A.", "Via del Presente 2", LocalDate.of(2015, 1, 1), null);
        societaService.inserisciSocieta(socRegolare);

        Dipendente dipAnomalo = new Dipendente("Marty", "McFly", LocalDate.of(2018, 5, 15), 30000, socAnomala);
        dipendenteService.inserisciDipendente(dipAnomalo);

        Dipendente dipRegolare = new Dipendente("Mario", "Rossi", LocalDate.of(2017, 3, 10), 25000, socRegolare);
        dipendenteService.inserisciDipendente(dipRegolare);


        List<Societa> risultati = societaService.cercaSocietaAnomale();

        if (risultati == null || risultati.isEmpty()) {
            throw new RuntimeException("test FAILED: Nessuna società anomala trovata!");
        }

        boolean trovataAnomala = risultati.stream().anyMatch(s -> s.getId().equals(socAnomala.getId()));
        boolean trovataRegolare = risultati.stream().anyMatch(s -> s.getId().equals(socRegolare.getId()));

        if (!trovataAnomala) {
            throw new RuntimeException("test FAILED: La società anomala non è stata rilevata!");
        }
        if (trovataRegolare) {
            throw new RuntimeException("test FAILED: Una società regolare è finita per sbaglio nella lista delle anomale!");
        }

        System.out.println("testSocietaAnomale ... OK\n");
    }




}
