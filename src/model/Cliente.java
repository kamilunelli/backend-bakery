
package model;

public class Cliente {
 
    //atributos
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private int totalPontosAcumulados;
    
    //construtor
    public Cliente(int id, String nome, String cpf, String telefone, int totalPontosAcumulados){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.totalPontosAcumulados = totalPontosAcumulados;
    }
    
    //construtor p novo cliente
    public Cliente(String nome, String cpf, String telefone){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.totalPontosAcumulados = 0;
    }
    
    //construtor vazio
    public Cliente(){
        
    }
    
    //setters e getters
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public String getCpf(){
        return this.cpf;
    }
    
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    
    public String getTelefone(){
        return this.telefone;
    }
    
    public void setTotalPontosAcumulados(int totalPontosAcumulados){
        this.totalPontosAcumulados = totalPontosAcumulados;
    }
    
    public int getTotalPontosAcumulados(){
        return this.totalPontosAcumulados;
    }
    
}
