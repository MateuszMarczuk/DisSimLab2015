package smo;

import dissimlab.simcore.SimControlException;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Mateusz Marczuk
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 27.03.2018.
 */
public class Kolejka {
    public class Element{
        Zgloszenie zgloszenie;
        int priorytet;

        public Element(Zgloszenie zg, int pr) {
            this.zgloszenie = zg;
            this.priorytet = pr;
        }
    }

    String typ; //lifo/fifo
    int liczbaMiejsc;
    boolean ograniczona;
    boolean priorytet;
    LinkedList<Element> lista = new LinkedList<Element>(); //zgloszenia
    Random generator = new Random();


    public Kolejka(String t, boolean o, int lm, boolean pr){
        this.typ = t;
        this.ograniczona = o;
        this.liczbaMiejsc = lm;
        this.priorytet = pr;
    }

    public void add(Zgloszenie zgloszenie){
        int prio = 0;
        if (priorytet){
             prio = generator.nextInt(10);
        } else {
             prio =1;
        }
        Element element = new Element(zgloszenie,prio);
        if ((ograniczona && liczbaMiejsc < size()) || (!ograniczona)){
            if (typ == "lifo"){
                lista.addFirst(element);
            } else {
                lista.addLast(element);
            }
        }
        else{
            try {
                zgloszenie.startNiecierpliwienia.interrupt();
            } catch (SimControlException e) {
                e.printStackTrace();
            }
        }
        if(this.priorytet)
        {
            for(int i=0; i<this.size()-1; i++)
            {
                for(int j=this.size()-1; j>=i+1; j--)
                {
                    if(this.lista.get(j).priorytet>this.lista.get(j-1).priorytet)
                    {
                        Element e=this.lista.get(j);
                        this.lista.set(j, this.lista.get(j-1));
                        this.lista.set(j-1, e);
                    }
                }
            }
        }


    }

    public int size(){
        return lista.size();
    }

    public Zgloszenie removeFirst(){
        Zgloszenie zgl = lista.getFirst().zgloszenie;
        lista.removeFirst();
        return zgl;
    }

    public Zgloszenie removeLast(){
        Zgloszenie zgl = lista.getLast().zgloszenie;
        lista.removeLast();
        return zgl;
    }

    public boolean remove(Zgloszenie zgl){
        for (int i =0; i<= size(); i++){
            if (lista.get(i).zgloszenie ==  zgl){
                lista.remove(i);
                return true;
            }
        }
        return false;
    }
}
