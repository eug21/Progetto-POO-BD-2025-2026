package controller;


//import per i metodi di Cliente
import dao.*;
import exception.*;
import implementazionePostgresDAO.*;
import model.*;

//import per i metodi di Veicolo
import javax.management.modelmbean.ModelMBeanOperationInfo;
import java.math.BigDecimal;
import java.util.List;


//import per metodi di filiale

//import per i metodi di contratto

import java.time.LocalDate;

//import per i metodi di responsabile

//import per i metodi di meccanico

//import per i metodi di riparazione
import java.util.Date;

/**
 *  Controller.
 */
public class Controller {

    private final ClienteDAO clienteDAO;
    private final FilialeDAO filialeDAO;
    private final VeicoloDAO veicoloDAO;
    private final MeccanicoDAO meccanicoDAO;
    private final ResponsabileDAO responsabileDAO;
    private final RiparazioneDAO riparazioneDAO;
    private final ContrattoDAO contrattoDAO;

    /**
     * Costruttore del Controller, include gli oggetti relativi alle implementazioni delle interfacce DAO necessari
     * per chiamarne i metodi.
     */
    public Controller (){
        this.filialeDAO = new FilialeDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
        this.veicoloDAO = new VeicoloDAOImpl();
       this.meccanicoDAO = new MeccanicoDAOImpl();
        this.responsabileDAO = new ResponsabileDAOImpl();
        this.riparazioneDAO = new RiparazioneDAOImpl();
        this.contrattoDAO = new ContrattoDAOImpl();

     }


    /**
     * Registra un cliente nel sistema.
     *
     * @param nome        nome
     * @param cognome     cognome
     * @param cf          codice fiscale
     * @param tipo        tipo patente a scelta dalla Enumeration TipoPatente
     * @param numPatente  numerp patente
     * @return  cliente
     * @throws DatiClienteNonValidi eccezione lanciata se i dati del cliente non sono validi, verifica mediante opportuno
     *                              metodo.
     */
//registrazione cliente
    public Cliente registraCliente(String nome, String cognome, String cf, TipoPatente tipo, String numPatente) throws DatiClienteNonValidi {
        Cliente cliente = new Cliente(nome, cognome, cf, tipo, numPatente);

        if (!cliente.verificaRegistrazione()) {
            throw new DatiClienteNonValidi("Dati cliente non validi.");

        }
        clienteDAO.save(cliente);
        return cliente;

    }

    //ricerco i clienti per numero di patente che uso come PK

    /**
     * Ricerca un cliente nel sistema mediante il suo numero di patente.
     *
     * @param numPatente numero patente
     * @return  cliente
     * @throws ClienteNonTrovatoException eccezione lanciata se il numero patente non trova nessuna corrispondenza, ciò vuol dire che banalamente il cliente non esiste.
     */
    public Cliente ricercaPerPatente(String numPatente) throws ClienteNonTrovatoException {
        if (numPatente == null || numPatente.isBlank()) {
            throw new ClienteNonTrovatoException("Numero patente non valido.");
        }
        Cliente cliente = clienteDAO.trovaPerPatente(numPatente);

        if (cliente == null) {
            throw new ClienteNonTrovatoException("Il cliente non esiste " + numPatente);

        }
        return cliente;

    }



    /**
     * Lista di tutti i clienti registrati nel sistema
     *
     * @return  lista dei clienti
     */
//trova tutti i clienti
    public List<Cliente> getTuttiClienti(){
        return clienteDAO.findAll();
    }

    /**
     * Aggiornamento numero di patente in caso di rinnovo del documento da parte del cliente.
     * Questa modifica consente di preservare lo storico dei contratti di un cliente che altrimenti andrebbe perduto eliminando il cliente e registrandolo con il nuovo
     * numero di patente da capo.
     *
     * @param patenteVecchia precedente numero di patente
     * @param patenteNuova   nuovo numero di patente
     * @throws DatiClienteNonValidi       eccezione lanciata se i dati del cliente non sono validi, verifica mediante opportuno
     *      *                              metodo.
     * @throws ClienteNonTrovatoException eccezione lanciata se il numero patente non trova nessuna corrispondenza, ciò vuol dire che banalamente il cliente non esiste.
     */
//cambio numero patente in caso di rinnnovo
    public void cambioPatente (String patenteVecchia, String patenteNuova) throws DatiClienteNonValidi, ClienteNonTrovatoException{
        if(patenteVecchia.isEmpty() || patenteVecchia == null || patenteNuova.isEmpty() || patenteNuova == null){
            throw new DatiClienteNonValidi("Dati inseriti non validi");
        }
        boolean check = clienteDAO.rinnovoPatente(patenteVecchia, patenteNuova);
        if(!check){
            throw new ClienteNonTrovatoException("Il cliente non esiste");
        }
    }


    // da ora in poi ci sono i metodi relativi ai veicoli


    /**
     * Aggiunge un veicolo nel sistema registrandolo .
     *
     * @param tipo tipo di  veicolo
     * @param targa targa
     * @param marca marca
     * @param modello modello
     * @param tariffa tariffa giornaliera
     * @param aggiunta numero di porte/ cilindrata/ carico massimo
     * @return the veicolo
     * @throws DatiVeicoloNonValidiException eccezione lanciata se i dati inseriti per quel veicolo non sono validi.
     */
    public Veicolo aggiungiVeicolo(String tipo, String targa, String marca, String modello, BigDecimal tariffa, String aggiunta) throws DatiVeicoloNonValidiException{
        Veicolo veicolo = null;
        switch (tipo){
           case "Auto":
               int porte = Integer.parseInt(aggiunta);
               veicolo = new Auto(targa, modello, marca, tariffa, StatoVeicolo.Disponibile, porte);
               break;
            case "Moto":
                int cilindrata = Integer.parseInt(aggiunta);
                veicolo = new Moto(targa, modello, marca, tariffa, StatoVeicolo.Disponibile, cilindrata);
                break;
            case "Furgone":
                float carico = Float.parseFloat(aggiunta);
                veicolo = new Furgone(targa, modello, marca, tariffa, StatoVeicolo.Disponibile, carico);
                break;
            default:
                throw new IllegalArgumentException("Tipo veicolo non supportato");
       }
       if(!veicolo.verificaDatiVeicolo()){
           throw new DatiVeicoloNonValidiException("Dati inseriti non validi.");
       }

        veicoloDAO.save(veicolo);
        return veicolo;
    }

    /**
     * Ricerca un veicolo nel sistema mediante il suo numero di targa.
     *
     * @param targa  targa
     * @return  veicolo
     * @throws VeicoloNonTrovatoException eccezione lanciata se non viene trovata alcuna corrispondenza con la targa inserita, ciò significa che il veicolo non esiste.
     */
    public Veicolo cercaTarga(String targa) throws VeicoloNonTrovatoException{
        if (targa == null || targa.isBlank()) {
            throw new VeicoloNonTrovatoException("La targa non e' valida.");
        }
        return veicoloDAO.trovaPerTarga(targa);
    }

    /**
     * Modifica lo stato di un veicolo.
     *
     * @param targa  targa
     * @param stato  stato attuale
     * @return the boolean
     * @throws VeicoloNonTrovatoException  eccezione lanciata se non viene trovata alcuna corrispondenza con la targa inserita, ciò significa che il veicolo non esiste.
     */
//aggiorno a db lo stato di un veicolo mediante l'uso di setStatoVeicolo dal model
    public boolean aggiornaStatoVeicolo(String targa, StatoVeicolo stato) throws VeicoloNonTrovatoException{
        Veicolo veicolo = cercaTarga(targa);
        if (veicolo == null) {
            throw new VeicoloNonTrovatoException("Veicolo non trovato " + targa);

        }
        veicolo.setStatoVeicolo(stato);
        veicoloDAO.update(veicolo);
        return true;
    }



    /**
     * Restituisce una lista dei veicoli disponibili.
     *
     * @return  veicoli disponibili
     */
    public List<Veicolo> getVeicoliDisponibili() {
        return veicoloDAO.cercaStato(StatoVeicolo.Disponibile);
    }

    /**
     * Restituisce una lista dei veicoli in manutenzione.
     *
     * @return  veicoli manutenzione
     */
    public List<Veicolo> getVeicoliManutenzione() {
        return veicoloDAO.cercaStato(StatoVeicolo.Manutenzione);
    }

    /**
     * Restituisce una lista dei  veicoli noleggiati.
     *
     * @return veicoli noleggiati
     */
    public List<Veicolo> getVeicoliNoleggiati() {
        return veicoloDAO.cercaStato(StatoVeicolo.Noleggiato);
    }



    //seguono i metodi della classe filiale


    /**
     * Aggiunge una filiale nel sistema registrandola mediante un codice univoco.
     *
     * @param codiceFiliale   codice filiale
     * @param via             via
     * @param citta           citta
     * @param cap             cap
     * @param numeroTelefono  numero telefono
     * @return  filiale
     * @throws DatiFilialeNonValidaException eccezione lanciata se i dati della filiale non risultano essere validi.
     */
//aggiungere una filiale a db
    public Filiale aggiungiFiliale(String codiceFiliale, String via, String citta, String cap, String numeroTelefono) throws DatiFilialeNonValidaException{
        Filiale filiale = new Filiale(codiceFiliale, via, citta, cap, numeroTelefono);
        if (!filiale.verificaDatiFiliale()) {
            throw new DatiFilialeNonValidaException("I dati inseriti non sono validi. ");
        }
        filialeDAO.save(filiale);
        return filiale;
    }

    /**
     * Ricerca di una filiale nel sistema mediante il suo codice univoco.
     *
     * @param codiceFiliale  codice filiale
     * @return  filiale
     * @throws FilialeNonTrovataException eccezione lanciata se non vi è alcuna corrispondenza tra il codice filiale fornito e il sistema, ciò vuol dire che tale filiale non esiste.
     */
//ricerca di una filiale mediante il suo codice univoco nel db
    public Filiale cercaConIdFiliale(String codiceFiliale) throws FilialeNonTrovataException{
        if (codiceFiliale == null || codiceFiliale.isBlank()) {
            throw new FilialeNonTrovataException("La filiale non esiste.");
        }
        return filialeDAO.trovaPerCodice(codiceFiliale);
    }


    /**
     * Modifica i dati di una filiale nel sistema.
     *
     * @param nuovoCodice       nuovo codice
     * @param nuovaVia          nuova via
     * @param nuovoCap          nuovo cap
     * @param nuovaCitta        nuova citta
     * @param nuovoNumTelefono  nuovo num telefono
     * @param codiceFiliale     codice filiale
     * @return the boolean
     * @throws DatiFilialeNonValidaException eccezione lanciata se i dati della filiale non risultano essere validi.
     * @throws FilialeNonTrovataException   eccezione lanciata se non vi è alcuna corrispondenza tra il codice filiale fornito e il sistema, ciò vuol dire che tale filiale non esiste.
     */
//modifica dei dati di una filiale
    public boolean modificaDatiFiliale(String nuovoCodice, String nuovaVia, String nuovoCap, String nuovaCitta, String nuovoNumTelefono, String codiceFiliale) throws DatiFilialeNonValidaException, FilialeNonTrovataException{
        Filiale filiale = cercaConIdFiliale(codiceFiliale);
        if (filiale == null) {
            throw new FilialeNonTrovataException("La filiale non esiste. ");
        }

        Filiale filialeTemp = new Filiale(nuovoCodice, nuovaVia, nuovaCitta, nuovoCap, nuovoNumTelefono);

        if (!filialeTemp.verificaDatiFiliale()) {
            throw new DatiFilialeNonValidaException("I dati inseriti non sono validi. ");
        }
        filiale.setCodiceFiliale(nuovoCodice);
        filiale.setCap(nuovoCap);
        filiale.setCitta(nuovaCitta);
        filiale.setVia(nuovaVia);
        filiale.setNumeroTelefono(nuovoNumTelefono);

        filialeDAO.update(filiale);
        return true;
    }

    /**
     * Lista delle filiali.
     *
     * @return  filiali
     */
//lista di tutte le filiali presenti a db
    public List<Filiale> getFiliali()  {
        return filialeDAO.getAll();
    }

    //seguono tutti i metodi che consentono di manipoalare i contratti


    /**
     * Aggiunge un nuovo contratto nel sistema.
     *
     * @param idFilialeRitiro    id filiale ritiro
     * @param idFilialeConsegna  id filiale consegna
     * @param dataInizio         data inizio
     * @param dataFine           data fine
     * @param prezzo             prezzo
     * @param cliente            cliente
     * @param veicolo            veicolo
     * @return the contratto
     * @throws VeicoloNonDisponibileException  eccezione lanciata se il numero patente non trova nessuna corrispondenza, ciò vuol dire che banalamente il cliente non esiste.
     * @throws ClienteNonTrovatoException      eccezione lanciata se il numero patente non trova nessuna corrispondenza, ciò vuol dire che banalamente il cliente non esiste.
     * @throws DateContrattoNonValideException eccezione lanciata se le date del contratto non sono valide. Esempio: date di fine antecedente alla data di inizio.
     * @throws PatenteNonValidaException       eccezione lanciata se la patente non è valida.
     */
//aggiunta di un contratto
    public Contratto aggiungiContratto(String idFilialeRitiro, String idFilialeConsegna, LocalDate dataInizio, LocalDate dataFine, BigDecimal prezzo, Cliente cliente, Veicolo veicolo) throws VeicoloNonDisponibileException, ClienteNonTrovatoException, DateContrattoNonValideException, PatenteNonValidaException {
        if (!veicolo.verificaDisponibile()) {
            throw new VeicoloNonDisponibileException("Il veicolo non e' disponibile. ");
        }
        if (!cliente.verificaPatente()) {
            throw new PatenteNonValidaException("Il cliente non ha una patente abilitata alla conduzione di tale veicolo. ");
        }

        Contratto contratto = new Contratto(idFilialeRitiro, idFilialeConsegna, veicolo.getTarga(), dataInizio, dataFine, BigDecimal.ZERO);
        if(!contratto.verificaDate()){
            throw new DateContrattoNonValideException("Le date del contratto non sono valide. ");
        }
        BigDecimal prezzoFinale = veicolo.getTariffaDie().multiply(BigDecimal.valueOf(contratto.getDurataNoleggio()));
        contratto.setPrezzo(prezzoFinale);
        veicolo.setStatoVeicolo(StatoVeicolo.Noleggiato);
        contrattoDAO.save(contratto, cliente.getNumeroPatente());
        veicoloDAO.update(veicolo);
        return contratto;
    }

    /**
     * Chiude un contratto nel sistema, aggiorna lo stato del relativo veicolo.
     *
     * @param contratto  contratto
     * @return  boolean
     * @throws VeicoloNonTrovatoException  eccezione lanciata se non viene trovata alcuna corrispondenza con la targa inserita, ciò significa che il veicolo non esiste.
     * @throws ContrattoNonValidoException eccezione lanciata se non viene trovato il contratto con quella targa.
     */
//metodo di chiusura di un contratto
    public boolean chiudiContratto(Contratto contratto) throws VeicoloNonTrovatoException, ContrattoNonValidoException {
        if(contratto == null ){
            throw new ContrattoNonValidoException("Il contratto non e' valido. ");
        }
        Veicolo veicolo = cercaTarga(contratto.getTargaVeicolo());
        if(veicolo == null){
            throw new VeicoloNonTrovatoException("Il veicolo non esiste.");
        }
        if(veicolo.getStatoVeicolo() == StatoVeicolo.Disponibile){
            throw new ContrattoNonValidoException("Il contratto e' già stato chiuso, il veicolo e' disponibile.");
        }
        veicolo.setStatoVeicolo(StatoVeicolo.Disponibile);
        veicoloDAO.update(veicolo);
        return true;
    }

    /**
     * Ritorna una lista dei contratti del cliente.
     *
     * @param cliente  cliente
     * @return  contratti cliente
     * @throws ClienteNonTrovatoException eccezione lanciata se il numero patente non trova nessuna corrispondenza, ciò vuol dire che banalamente il cliente non esiste.
     */
//metodo che mostra tutti i contratti relativi a un cliente
    public List<Contratto> getContrattiCliente(Cliente cliente) throws ClienteNonTrovatoException {
        if (cliente == null) {
            throw new ClienteNonTrovatoException("Il cliente non e' stato trovato. ");
        }
        return contrattoDAO.trovaPerCliente(cliente.getNumeroPatente());
    }

    /**
     * Ritorna i contratti  della filiale.
     *
     * @param filiale  filiale
     * @return  contratti filiale
     * @throws FilialeNonTrovataException  eccezione lanciata se non vi è alcuna corrispondenza tra il codice filiale fornito e il sistema, ciò vuol dire che tale filiale non esiste.
     */
//lista dei contratti attivi della filiale
    public List<Contratto> getContrattiFiliale(Filiale filiale) throws FilialeNonTrovataException {
        if (filiale == null) {
            throw new FilialeNonTrovataException("La filiale non esiste. ");
        }
        return contrattoDAO.trovaPerFiliale(filiale.getCodiceFiliale());
    }

    /**
     * Calcola il costo del noleggio in base alle date e alla tariffa giornaliera del veicolo.
     *
     * @param veicolo     veicolo
     * @param dataInizio  data inizio
     * @param dataFine    data fine
     * @return  costo
     * @throws DateContrattoNonValideException  eccezione lanciata se le date del contratto non sono valide. Esempio: date di fine antecedente alla data di inizio.
     */
//calcolo del prezzo di un contratto in anteprima, prima della conferma e della successiva creazione di esso
    public BigDecimal calcolaCostoNoleggio(Veicolo veicolo, LocalDate dataInizio, LocalDate dataFine) throws DateContrattoNonValideException{
        Contratto contrattoTemp = new Contratto(null , null, null,dataInizio, dataFine, BigDecimal.ZERO);
        if(!contrattoTemp.verificaDate()){
            throw new DateContrattoNonValideException("Le date inserite non sono valide. ");
        }

        return veicolo.getTariffaDie().multiply(BigDecimal.valueOf(contrattoTemp.getDurataNoleggio()));
    }

    /**
     * Lista dei contratti per periodo di date .
     *
     * @param dataInizio  data inizio
     * @param dataFine    data fine
     * @return  list
     * @throws DateContrattoNonValideException  eccezione lanciata se le date del contratto non sono valide. Esempio: date di fine antecedente alla data di inizio.
     */
//lista dei contratti filtrata per periodo di date
    public List <Contratto> contrattiPerPeriodo (LocalDate dataInizio, LocalDate dataFine) throws DateContrattoNonValideException {
        Contratto temp = new Contratto(null, null, null, dataInizio, dataFine, BigDecimal.ZERO);
        if(!temp.verificaDate()){
            throw new DateContrattoNonValideException("Le date del contratto non sono valide. ");
        }
        return contrattoDAO.trovaPerPeriodo(dataInizio, dataFine);
    }

    //seguono i metodi della classe responsabile


    /**
     * Aggiunge un responsabile nel sistema registrandolo mediante un codice identificativo univoco.
     *
     * @param idResponsabile  id responsabile
     * @param nome            nome
     * @param cognome         cognome
     * @param mail            mail
     * @return  responsabile
     * @throws DatiResponsabileNonValidiException eccezione lanciata se i dati inseriti del responsabili non sono validi, previa verifica.
     * @throws  ResponsabileNonDisponibileException eccezione lanciata se il responsabile indicato non è disponibile.
     */
//aggiunge un responsabile
    public Responsabile aggiungiResponsabile(String idResponsabile, String nome, String cognome, String mail) throws DatiResponsabileNonValidiException , ResponsabileNonDisponibileException{
    if(idResponsabile.isBlank() || nome.isBlank()||cognome.isBlank()||mail.isBlank()|| idResponsabile == null || nome == null || cognome == null || mail == null){
        throw new DatiResponsabileNonValidiException("I dati del responsabile non sono validi");
    }
    Responsabile responsabile = new Responsabile(idResponsabile, nome, cognome, mail);

    if (!responsabile.isDisponibile()) {
            throw new ResponsabileNonDisponibileException("Dati responsabile non validi");
        }

        responsabileDAO.save(responsabile);
        return responsabile;
    }

    /**
     * Cerca un resèpnsabile nel sistema mediante il suo codice identificativo univoco.
     *
     * @param idResponsabile  id responsabile
     * @return  responsabile
     * @throws ResponsabileNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice del responsabile fornito e il sistema, ciò significa che il responsabile non esiste.
     */
//ricerca responsabile per id
    public Responsabile cercaResponsabile(String idResponsabile) throws ResponsabileNonTrovatoException {
        if (idResponsabile == null || idResponsabile.isBlank()) {
            throw new ResponsabileNonTrovatoException("ID responsabile non valido.");
        }
        return responsabileDAO.trovaPerID(idResponsabile);
    }

    /**
     * Elimina un responsabile dal sistema previa ricerca mediante codice identificativo.
     *
     * @param idResponsabile  id responsabile
     * @return  boolean
     * @throws ResponsabileNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice del responsabile fornito e il sistema, ciò significa che il responsabile non esiste.
     */
//elimina un responsabile
    public boolean eliminaResponsabile(String idResponsabile) throws ResponsabileNonTrovatoException, ResponsabileNonDisponibileException {
        Responsabile responsabile = cercaResponsabile(idResponsabile);
        if (responsabile == null) {
            throw new ResponsabileNonTrovatoException("Il responsabile non esiste" + idResponsabile);
        }
        String verificaFiliale = responsabileDAO.ottieniFiliale(idResponsabile);
        if(verificaFiliale != null){
            throw new ResponsabileNonDisponibileException("Il responsabile non e' disponibile");
        }
      responsabileDAO.delete(idResponsabile);
        return true;
    }

    /**
     * Lista di  tutti i responsabili.
     *
     * @return the tutti responsabili
     */
//lista di tutti i responsabili
    public List<Responsabile> getTuttiResponsabili()  {
       return responsabileDAO.findAll();
    }

    /**
     * Assegna un responsabile a una filiale.
     *
     * @param idResponsabile the id responsabile
     * @param codiceFiliale  the codice filiale
     * @return the boolean
     * @throws ResponsabileNonTrovatoException the responsabile non trovato exception
     * @throws FilialeNonTrovataException      the filiale non trovata exception
     */
//assegna un responsabile a una filiale
    public boolean assegnaResponsabileAFiliale(String idResponsabile, String codiceFiliale) throws ResponsabileNonTrovatoException, FilialeNonTrovataException ,ResponsabileNonDisponibileException{

        Responsabile responsabile = cercaResponsabile(idResponsabile);
        if (responsabile == null) {
            throw new ResponsabileNonTrovatoException("Il responsabile non esiste" + idResponsabile);
        }
        String verificaFiliale = responsabileDAO.ottieniFiliale(idResponsabile);
        if(verificaFiliale != null){
            throw new ResponsabileNonDisponibileException("Il responsabile non e' disponibile");
        }
        Filiale filiale = cercaConIdFiliale(codiceFiliale);
        if (filiale == null) {
            throw new FilialeNonTrovataException("La filiale non esiste" + codiceFiliale);
        }
        responsabile.setDisponibile(false);
       responsabileDAO.assegnaFiliale(idResponsabile, codiceFiliale);
        responsabileDAO.update(responsabile);
        return true;
    }

    /**
     * Rimuovi responsabile da filiale boolean.
     *
     * @param idResponsabile  id responsabile
     * @param codiceFiliale   codice filiale
     * @return  boolean
     * @throws ResponsabileNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice del responsabile fornito e il sistema, ciò significa che il responsabile non esiste.
     * @throws FilialeNonTrovataException       eccezione lanciata se non vi è alcuna corrispondenza tra il codice filiale fornito e il sistema, ciò vuol dire che tale filiale non esiste.
     */
//rimuove un responsabile da una filiale
    public boolean rimuoviResponsabileDaFiliale(String idResponsabile, String codiceFiliale) throws ResponsabileNonTrovatoException, FilialeNonTrovataException {

        Responsabile responsabile = cercaResponsabile(idResponsabile);
        if (responsabile == null) {
            throw new ResponsabileNonTrovatoException("Il responsabile non esiste" + idResponsabile);
        }
        Filiale filiale = cercaConIdFiliale(codiceFiliale);
        if (filiale == null) {
            throw new FilialeNonTrovataException("La filiale non esiste" + codiceFiliale);
        }

        responsabile.setDisponibile(true);
       responsabileDAO.rimuoviDaFiliale(idResponsabile);
       responsabileDAO.update(responsabile);
        return true;
    }

    /**
     * Modifica email del responsabile.
     *
     * @param idResponsabile  id responsabile
     * @param email           email
     * @throws ResponsabileNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice del responsabile fornito e il sistema, ciò significa che il responsabile non esiste.
     */
//modifica email di un responsabile
    public void modificaEmail (String idResponsabile, String email) throws ResponsabileNonTrovatoException{
        if(!email.contains("@")){
            throw new IllegalArgumentException("Formato mail non valido.");
        }
        boolean successo = responsabileDAO.modificaEmail(idResponsabile, email);
        if(!successo){
            throw new ResponsabileNonTrovatoException("Il responsabile non esiste.");
        }
    }

    /**
     * Ricava il codice filiale del responsabile sfruttando il database
     * @param idResponsabile idResponsabile
     * @return codiceFiliale
     */
    //ricava il codice filiale del responsabile mediante il dao
    public String filialeResponsabile (String idResponsabile){
        return responsabileDAO.ottieniFiliale(idResponsabile);
    }

    //metodi  per meccanico


    /**
     * Aggiunge un meccanico nel sistema registrandolo mediante un codice identificativo univoco.
     *
     * @param idMeccanico  id meccanico
     * @param nome         nome
     * @param cognome      cognome
     * @return  meccanico
     * @throws DatiMeccanicoNonValidiException eccezione lanciata se i dati inseriti per il meccanico non sono validi, previa verifica.
     */
    public Meccanico aggiungiMeccanico(String idMeccanico, String nome, String cognome) throws DatiMeccanicoNonValidiException {
        if(idMeccanico.isBlank() || nome.isBlank()||cognome.isBlank()|| idMeccanico== null || nome == null || cognome == null){
            throw new DatiMeccanicoNonValidiException("I dati del meccanico non sono validi");
        }
        Meccanico meccanico = new Meccanico(idMeccanico, nome, cognome);
        meccanicoDAO.save(meccanico);
        return meccanico;
    }

    /**
     * Cerca un meccanico nel sistema mediante il suo codice.
     *
     * @param idMeccanico  id meccanico
     * @return  meccanico
     * @throws MeccanicoNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice identificativo fornito e il sistema, ciò vuol dire che il meccanico non esiste.
     */
    public Meccanico cercaMeccanico(String idMeccanico) throws MeccanicoNonTrovatoException{
        if(idMeccanico.isBlank()||idMeccanico == null){
            throw new MeccanicoNonTrovatoException("Il meccanico non esiste");
        }
        return meccanicoDAO.trovaPerID(idMeccanico);


    }

    /**
     * Elimina un meccanico dal sistema cercandolo mediante il suo codice identificativo.
     *
     * @param idMeccanico  id meccanico
     * @return  boolean
     * @throws MeccanicoNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice identificativo fornito e il sistema, ciò vuol dire che il meccanico non esiste.
     */
    public boolean eliminaMeccanico(String idMeccanico)throws MeccanicoNonTrovatoException{
        if(idMeccanico.isBlank()||idMeccanico == null){
            throw new MeccanicoNonTrovatoException("Il meccanico non esiste");
        }
        Meccanico meccanico = cercaMeccanico(idMeccanico);
       meccanicoDAO.delete(idMeccanico);
        return true;
    }

    /**
     * Modifica lo stato del meccanico
     *
     * @param idMeccanico  id meccanico
     * @param stato        stato
     * @return  boolean
     * @throws MeccanicoNonTrovatoException eccezione lanciata se non vi è alcuna corrispondenza tra il codice identificativo fornito e il sistema, ciò vuol dire che il meccanico non esiste.
     */
    public boolean cambiaStatoMeccanico(String idMeccanico,boolean stato) throws MeccanicoNonTrovatoException{
        if(idMeccanico.isBlank()||idMeccanico == null){
            throw new MeccanicoNonTrovatoException("Il meccanico non esiste");
        }
        Meccanico meccanico = cercaMeccanico(idMeccanico);

        meccanico.setDisponibile(stato);
        return true;
    }

    /**
     * Lista  dei meccanici.
     *
     * @return the list
     */
    public List <Meccanico> listaMeccanici(){
      return meccanicoDAO.findAll();

    }

    //seguono i metodi della classe riparazioni


    /**
     * Chiudi una riparazione liberando il veicolo.
     *
     * @param targaVeicolo  targa veicolo
     * @param costoFinale   costo finale
     * @param data data
     * @return  boolean
     * @throws RiparazioneNonTrovateException    eccezione lanciata se non vi è alcuna corrispondenza tra la targa del veicolo e il sistema, ciò vuol dire che il veicolo non è in riparazione attualmente.
     * @throws DatiRiparazioneNonValidiException eccezione lanciata se i dati inseriti della riparazione non sono validi, previa verifica.
     */
//chiude la riparazione e aggiorna lo stato del veicolo
    public boolean chiudiRiparazioneVeicolo( String targaVeicolo, float costoFinale, Date data) throws RiparazioneNonTrovateException, DatiRiparazioneNonValidiException {

        if (targaVeicolo == null || targaVeicolo.isBlank() || costoFinale < 0) {
            throw new DatiRiparazioneNonValidiException("I dati non sono validi");
        }
       Riparazione riparazione = cercaRiparazionePerTarga(targaVeicolo , data);
        riparazione.setCostoFinale(costoFinale);
        Veicolo veicolo = cercaTarga(targaVeicolo);
        if(veicolo  != null){
            veicolo.setStatoVeicolo(StatoVeicolo.Disponibile);
            veicoloDAO.update(veicolo);
        }
      riparazioneDAO.update(riparazione);
        return true;
    }

    /**
     * Aggiungi riparazione una riparazione nel sistema.
     *
     * @param descrizioneProblema  descrizione problema
     * @param costoStimato         costo stimato
     * @param dataRiparazione      data riparazione
     * @param costoFinale          costo finale
     * @param targaVeicolo         targa veicolo
     * @return  riparazione
     * @throws DatiRiparazioneNonValidiException eccezione lanciata se i dati inseriti della riparazione non sono validi, previa verifica.
     */
//aggiunge una riparazione controllando la disponbilità del meccanico
    public Riparazione aggiungiRiparazione(String descrizioneProblema, float costoStimato, Date dataRiparazione, float costoFinale, String targaVeicolo) throws DatiRiparazioneNonValidiException {

        if(descrizioneProblema == null || targaVeicolo == null || dataRiparazione == null || descrizioneProblema.isBlank() || targaVeicolo.isBlank() || costoStimato < 0 || costoFinale < 0){
            throw new DatiRiparazioneNonValidiException("La riparazione non e' valida");
        }
        Riparazione riparazione = new Riparazione(costoStimato, descrizioneProblema,costoFinale, dataRiparazione,targaVeicolo);
        Veicolo veicolo = cercaTarga(targaVeicolo);
        veicolo.setTarga(targaVeicolo);
        if(veicolo.getStatoVeicolo() != StatoVeicolo.Disponibile){
            throw new DatiRiparazioneNonValidiException("Il veicolo non e' disponibile.");
        }
        riparazioneDAO.save(riparazione);
        veicolo.setStatoVeicolo(StatoVeicolo.Manutenzione);
        veicoloDAO.update(veicolo);


        return riparazione;
    }

    /**
     * Cerca riparazione mediante la targa del veicolo attualmente in manutenzione.
     *
     * @param targaVeicolo  targa veicolo
     * @return  riparazione
     * @throws DatiRiparazioneNonValidiException eccezione lanciata se i dati inseriti della riparazione non sono validi, previa verifica.
     * @throws RiparazioneNonTrovateException    eccezione lanciata se non vi è alcuna corrispondenza tra la targa del veicolo e il sistema, ciò vuol dire che il veicolo non è in riparazione attualmente.
     */
    public Riparazione cercaRiparazionePerTarga(String targaVeicolo, Date data) throws DatiRiparazioneNonValidiException, RiparazioneNonTrovateException{
        if (targaVeicolo == null || targaVeicolo.isBlank()) {
            throw new DatiRiparazioneNonValidiException("La targa non è valida.");
        }
        Riparazione riparazione = riparazioneDAO.cercaPerTarga(targaVeicolo, data);

        if (riparazione == null) {
            throw new RiparazioneNonTrovateException("Riparazione non trovata");
        }
        return riparazione;
    }

    /**
     * Lista di tutte le riparazioni.
     *
     * @return  list
     */
//lista di tutte le riparazioni
    public List<Riparazione> getTutteRiparazioni(){
      return riparazioneDAO.findAll();

    }

}




