import java.lang.reflect.*;

public class ArvoreBinariaDeBusca <X extends Comparable<X>>
{
    private class No
    {
        private No esq;
        private X  info;
        private No dir;

        public No (No e, X i, No d)
        {
            this.esq  = e;
            this.info = i;
            this.dir  = d;
        }

        public No (X i)
        {
            this (null,i,null);
        }

        public void setEsq (No e)
        {
            this.esq = e;
        }

        public void setInfo (X i)
        {
            this.info = i;
        }

        public void setDir (No d)
        {
            this.dir = d;
        }

        public No getEsq ()
        {
            return this.esq;
        }

        public X getInfo ()
        {
            return this.info;
        }

        public No getDir ()
        {
            return this.dir;
        }

        public boolean equals (Object obj)
        {
            if (this==obj)
                return true;

            if (obj==null)
                return false;

            if (this.getClass()!=obj.getClass())
                return false;

            No no = (No)obj;

            if (!this.info.equals(no.info))
                return false;

            return true;
        }

        public String toString ()
        {
            return ""+this.info;
        }

        /*
        public int hashCode ()
        {
        }

        public No (No modelo) throws Exception
        {
        }

        public Object clone ()
        {
        }
        */
    }

    private No raiz;

    public ArvoreBinariaDeBusca ()
    {
        this.raiz = null;
    }

    private X meuCloneDeX (X modelo)
    {
      //return modelo.clone();

        X ret=null;

        try
        {
            Class<?> classe = modelo.getClass();
            Class<?>[] tiposDosParametrosFormais = null;
            Method metodo = classe.getMethod ("clone", tiposDosParametrosFormais);
            Object[] parametrosReais = null;
            ret = (X)metodo.invoke (modelo, parametrosReais);
        }
        catch (NoSuchMethodException erro)
        {}
        catch (InvocationTargetException erro)
        {}
        catch (IllegalAccessException erro)
        {}

        return ret;
    }

    // fazer versao iterativa do insira

    public void insira (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        this.insira (this.raiz, i);
    }

    private No insira (No r, X i) throws Exception
    {
        if (r==null)
            if (i instanceof Cloneable)
                return new No (this.meuCloneDeX(i));
            else
                return new No (i);

        int comp=i.compareTo(r.getInfo());

        if (comp==0)
            throw new Exception ("Informacao 000");

        if (comp<0) // => inserir ? esquerda
            r.setEsq (this.insira (r.getEsq(), i));
        else // comp// => inserir ? direita
            r.setDir (this.insira (r.getDir(), i));

        return null;
    }

    // fazer versao iterativa do tem

    public boolean tem (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        return this.tem (this.raiz, i);
    }

    private boolean tem (No r, X i) throws Exception
    {
        if (r==null)
            return false;

        int comp=i.compareTo(r.getInfo());

        if (comp==0)
            return true;

        if (comp<0)
            return this.tem (r.getEsq(),i);

        //comp>0
        return this.tem (r.getDir(),i);
    }

    public void jogueFora (X i) throws Exception
    {
        if (r==null)
            throw new Exception ("Arvore vazia");

        No  ant=null, atu=this.raiz;

        for(;;) //forEVER
        {
            int comp=i.compareTo(atu.getInfo());

            if (comp==0)
                break;

            ant=atu;

            if (comp<0)
                atu=atu.getEsq();
            else
                atu=atu.getDir();

            if (atu==null)
                throw new Exception ("Excluindo inexistente");
        }

        if (atu.getEsq()==null && atu.getDir()==null) // ? folha?
            if (atu==this.raiz)
                this.raiz=null;
            else
                if (ant.getEsq()==atu) // est? ? esquerda
                    ant.setEsq (null);
                else // (ant.getDir()==atu) // est? ? direita
                    ant.setDir (null);
        else // nao ? folha
            if (atu.getEsq()==null && atu.getDir()!=null)
                if (atu==this.raiz)
                    this.raiz=atu.getDir();
                else
                    ant.setDir (atu.getDir());
            else
                if (atu.getEsq()!=null && atu.getDir()==null)
                    if (atu==this.raiz)
                        this.raiz=atu.getEsq();
                    else
                        ant.setEsq (atu.getEsq());
                else // atu.getEsq()!=null && atu.getDir()!=null
                {    // nao pode excluir o no; tem que substituir a info

                    boolean tiraDaEsq = ((int)(Math.random()*2))==0;

                    No aRemover = atu;

                    if (tiraDaEsq)
                    {
                        ant = atu;
                        atu = atu.getEsq();
                        while (atu.getDir()!=null)
                        {
                           ant=atu;
                           atu=atu.getDir();
                        }
                        X substituta = atu.getInfo();
                        ant.setDir (atu.getEsq());
                        aRemover.setInfo (substituta);
                    }
                    else
                    {
                        ant = atu;
                        atu = atu.getDir();
                        while (atu.getEsq()!=null)
                        {
                           ant=atu;
                           atu=atu.getEsq();
                        }
                        X substituta = atu.getInfo();
                        ant.setEsq (atu.getDir());
                        aRemover.setInfo (substituta);
                    }
                }
    }

    public void jogueFora (X i) throws Exception
    {
        // fazer versao nao recursiva
    }

    public String toString ()
    {
        return this.toString (this.raiz);
    }

    private String toString (No r)
    {
        if (r==null)
            return "()";

        return "("+
               this.toString(r.getEsq())+
               r.getInfo()+
               this.toString(r.getDir())+
               ")";
    }

    public boolean equals (Object obj)
    {
        // fazer
    }

    public int hashCode ()
    {
        // fazer
    }

    public ListaOrdenadaDuplamenteLigada (ListaOrdenadaDuplamenteLigada<X> modelo) throws Exception
    {
        // fazer
    }

    public Object clone ()
    {
        // fazer
    }

    private int getQtdInfo (No r)
    {
        if (r==null)
            return 0;

        return this.getQtdInfo (r.getEsq())+
               1+
               this.getQtdInfo (r.getDir());
    }

    public int getQtdInfo ()
    {
        return this.getQtdInfo (this.raiz);
    }

    public boolean isBalanceada ()
    {
        return this.isBalanceada (this.raiz);
    }

    public boolean isBalanceada (No r)
    {
        if (r==null)
            return true;

        int diferenca=Math.abs(
                      this.getQtdInfo(r.getEsq())-
                      this.getQtdInfo(r.getDir()));

        if (diferenca>1)
            return false;

        if (!this.isBalanceada(r.getEsq()))
            return false;

        if (!this.isBalanceada(r.getDir()))
            return false;

        return true;
    }

    public void balanceieSe ()
    {
        this.balanceie (this.raiz);
    }

    private void balanceie (No r)
    {
        // fa?a
    }

    public int getQtdFolhas ()
    {
        // fa?a
    }

    public int getQtdNosDeDerivacao ()
    {
        // fa?a
    }

    public int getQtdInfoNoNivel (int nivel) throws Exception
    {
        // fa?a
    }

    public int getNivelDasFolhasComMenorNivel () throws Exception
    {
        // fa?a
    }

    public int getNivelDasFolhasComMaiorNivel () throws Exception
    {
        // fa?a
    }

    public boolean estruturaIgual (ArvoraBinariaDeBusca<X> outra) throws Exception
    {
        // fa?a
    }

    public ListaOrdenadaSimples<X> getListaElementos ()
    {
        // fa?a
    }

    public boolean isSimetrica ()
    {
        // fa?a
    }
}