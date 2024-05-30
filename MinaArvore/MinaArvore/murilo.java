import java.lang.reflect.*;

public class ArvoreBinariaDeBusca<X extends Comparable<X>>
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

        public No getEsq ()
        {
            if(this.esq == null)
                return null;
            else
                return this.esq;
        }

        public X getInfo ()
        {
            if(this.info == null)
                return null;
            else
                return this.info;
        }

        public No getDir ()
        {
            if(this.dir == null)
                return null;
            else
                return this.dir;
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
    }

    private No raiz;

    public ArvoreBinariaDeBusca ()
    {
        this.raiz = null;
    }

    private X meuCloneDeX (X x)
    {
        X ret=null;

        try
        {
          //ret = (X)x.clone();
            Class<?> classe = x.getClass();
            Class<?>[] tipoParametroFormal = null; // null pq clone tem 0 parametros
            Method metodo = classe.getMethod ("clone", tipoParametroFormal);
            Object[] parametroReal = null; // null pq clone tem 0 parametros
            ret = (X)metodo.invoke (x, parametroReal);
        }
        catch (Exception erro)
        {}

        return ret;
    }

    public int getQtdNos ()
    {
        return getQtdNos (this.raiz);
    }

    private int getQtdNos (No r)
    {
        if (r == null)
            return 0;

        return 1 + getQtdNos(r.getEsq()) + getQtdNos(r.getDir());
    }

    // daqui para frente tudo tem que ser repensado

    public void insira (X i) throws Exception
    {
        if (i == null)
            throw new Exception ("Informacao ausente.");

        X info;
        
        if (i instanceof Cloneable)
            info = meuCloneDeX (i);
        else
            info = i;

        this.raiz = insira (this.raiz, info);
    }

    private No insira (No r, X i) throws Exception
    {
        if (r == null)
            return new No (null, i, null);

        int comp = i.compareTo(r.getInfo());

        if (comp < 0 && r.getEsq() == null)
        {
            No novo = new No(null, i, null);
            r.setEsq(novo); //print(/r/n)
            
            return r;
        }
        
        if(comp > 0 && r.getDir() == null)
        {
            No novo = new No(null, i, null);
            r.setDir(novo);
            
            return r;
        }
        
        if (comp == 0)
            throw new Exception ("Informacao repetida.");

        if(comp < 0)
        {
            r.setEsq( insira(r.getEsq(), i) );
            return r;
        }
        else
        {
            r.setDir( insira(r.getDir(), i) );
            return r;
        }
    }

    
    public boolean tem (X i) throws Exception
    {
        if(i == null)
            throw new Exception("Parametro nulo.");
        
        if(this.raiz == null)
            throw new Exception("Arvore esta vazia.");
        
        if(tem(this.raiz, i))
            return true;
        else
            return false;
    }
    
    private boolean tem(No r, X i)
    {
        if(r == null)        
            return false;
        
        int comp = i.compareTo(r.getInfo());
        
        if(comp == 0)
            return true;
        
        if(comp < 0 && r.getEsq() == null)
            return false;
        
        if(comp > 0 && r.getDir() == null)
            return false;
        
        if(comp < 0)
            return tem(r.getEsq(), i);
        else    
            return tem(r.getDir(), i);
    }
    
    public void remova(X i)throws Exception
    {
        if(i == null)
            throw new Exception("Parametro nulo.");
        
        this.raiz = remova(null, this.raiz, i);
    }
    
    private No remova(No a, No r, X i)
    {
        if(r == null)   
            return r;            
        
        No aux = null;  
        No anterior = a;
        No atual = r;
        
        /================================== PRIMEIRA PARTE ==================================/
        if(i.equals(this.raiz.getInfo()) && this.raiz.getDir() == null && this.raiz.getEsq() == null)
        {
            this.raiz = null;
            return this.raiz;
        }
        
        if(i.equals(this.raiz.getInfo()) && this.raiz.getDir() != null && this.raiz.getEsq() == null)
        {
            this.raiz = this.raiz.getDir();
            return this.raiz;
        }
        
        if(i.equals(this.raiz.getInfo()) && this.raiz.getDir() == null && this.raiz.getEsq() != null)
        {
            this.raiz = this.raiz.getEsq();
            return this.raiz;
        }     
        
        /================================== SEGUNDA PARTE ==================================/
        int comp = i.compareTo(atual.getInfo());
        
        if(comp == 0)
        {
            if(anterior != null && atual.getDir() == null && atual.getEsq() == null)
            {
                if(atual == anterior.getEsq())
                    anterior.setEsq(null);
                else
                    anterior.setDir(null);

                return anterior;
            }

            if(anterior != null && atual.getDir() != null && atual.getEsq() == null)
            {
                if(atual == anterior.getEsq())
                    anterior.setEsq(atual.getDir());
                else
                    anterior.setDir(atual.getDir());

                return anterior;
            }

            if(anterior != null && atual.getDir() == null && atual.getEsq() != null)
            {
                if(atual == anterior.getEsq())
                    anterior.setEsq(atual.getEsq());
                else
                    anterior.setDir(atual.getEsq());

                return anterior;
            }
            
            if(anterior != null && atual.getDir() != null && atual.getEsq() != null)
            {
                aux = remova(atual, atual.getDir(), i);
                atual.setInfo(aux.getInfo());
                return aux;
            }
            
            if(anterior == null)
            {
                aux = remova(atual, atual.getDir(), i);
                atual.setInfo(aux.getInfo());
                return atual;
            }
        }
        
        /================================== TERCEIRA PARTE ==================================/
        if(atual.getEsq() == null && atual.getDir() != null)
        {
            aux = atual;
            
            if(atual == anterior.getEsq())
            {
                anterior.setEsq(atual.getDir());
                return aux;
            }
                
        }
        
        if(comp > 0)
        {
            if(i.equals(this.raiz.getInfo()))
            {
                aux = remova(atual, atual.getDir(), i);
                return aux;                  
            }
            remova(atual, atual.getDir(), i);
            return atual;  
        }
        else
        {  
            if(i.equals(this.raiz.getInfo()))
            {
                if(atual.getEsq() != null && atual.getDir() == null)
                    aux = remova(atual, atual.getEsq(), i);
                else
                    aux = remova(atual, atual.getDir(), i);
                
                
                return aux;                  
            }
            remova(atual, atual.getEsq(), i);
            return atual;
        }
    }
    
    public boolean balaceada()
    {
        double ret =  balanceada(this.raiz, null);
        
        if(ret == 0)
            return true;
        else
            return false;
    }
    
    private double balanceada(No r, No a)
    {
        No anterior = a;
        
        double esquerda = 0;
        double direita = 0;
        
        if(r == null)
            return 0;
        
        if(r == this.raiz)
        {
            if(r.getDir() == null && r.getEsq() == null)
                return 1;
            
            anterior = r;
            
            direita = balanceada(r.getDir(), anterior);
            esquerda = balanceada(r.getEsq(), anterior)  
            
            System.out.println("DIREITA " + direita);
            System.out.println("ESQUERDA " + esquerda);
            
            double ret = direita - esquerda;
            
            if(ret == -1 || ret == 1 ||  ret == 0)
                return 0;
            else
                return 1;
        }
        
        if(r.getDir() == null && r.getEsq() == null)
        {
            if(r == anterior.getDir())
            {
                if(anterior.getEsq() != null)
                    return 0.5;
                else
                    return 1;
            }
            else
            {
                if(anterior.getDir() != null)
                    return 0.5;
                else
                    return  1;
            }       
        }

        if(r == anterior.getDir())
        {
            if(anterior.getEsq() != null && anterior != this.raiz)
            {
                anterior = r;

                double i = 0.5;
                i += balanceada(r.getDir(),anterior);

                return i += balanceada(r.getEsq(), anterior);
            }
            else
            {
                anterior = r;

                double i = 1;
                i += balanceada(r.getDir(),anterior);

                return i += balanceada(r.getEsq(), anterior);
            }
        }else
        {
            if(anterior.getDir() != null && anterior != this.raiz)
            {
                anterior = r;

                double i = 0.5;
                i += balanceada(r.getDir(),anterior);

                return i += balanceada(r.getEsq(), anterior);
            }
            else
            {
                anterior = r;

                double i = 1;
                i += balanceada(r.getDir(),anterior);

                return i += balanceada(r.getEsq(), anterior);
            }
        }
    }
    
    public X getRaiz() 
    {
        if(this.raiz != null)
            return this.raiz.getInfo();
        else
            return null;
    }
    //==============================METODO OBRIGATORIOS
    
    public boolean equals (Object obj)
    {
        if(obj == null)
            return false;
        
        if(this == obj)
            return true;
        
        if(this.getClass() != obj.getClass())
            return false;
        
        No parametro = (No)obj;
        
        if(equals(this.raiz, parametro))
            return true;
        else 
            return false;
    }
    
    private boolean equals(No r, No p)
    {   
        boolean direita = false;
        boolean esquerda = false;
        
        if(r == null && p != null)
            return false;
        
        if(r != null && p == null)
            return false;
            
        if(r == null && p == null)
            return true;
        
        if(!r.getInfo().equals(p.getInfo()))
            return false;
            
        direita = equals(r.getDir(), p.getDir());
        esquerda = equals(r.getEsq(), p.getEsq());
        
        if(direita && esquerda)
            return true;
        
        return false;
    }
    
    @Override
    public String toString()
    {
        String ret = " ";
        
        return ret += "(" + toString(this.raiz) + ")";
    }
    
    private String toString (No r)
    {
        String ret = " ";
        
        if(r == null)
        {   
            return " ";        
        }
        
        if(r.getEsq() != null && r.getDir() != null)
        {
            ret += "(" + toString(r.getEsq()) + ")";
            return ret += r.getInfo() + "(" + toString(r.getDir()) + ")";
        }
        
        if(r.getEsq() != null && r.getDir() == null)
        {
            return ret += "(" + toString(r.getEsq()) + ")" + r.getInfo();
        }
        
        if(r.getDir() != null && r.getEsq() == null)
        {
            return ret += r.getInfo() + "(" + toString(r.getDir()) + ")";
        }
        
        if(r.getEsq() == null && r.getDir() == null)
        {
            return ret += r.getInfo(); 
        }
        
        return ret;
    }
    
    
    public int hashCode ()
    {
        int ret = hashCode(this.raiz);   
        return ret;
    }
    
    private int hashCode(No r)
    {
        if(r == null)
            return 0;
        
        int ret = 666;
        
        ret = ret * 7 + r.getInfo().hashCode();
        
        ret = hashCode(r.getDir());
        ret = hashCode(r.getEsq());
        
        return ret;
    }
    
    
    public ArvoreBinariaDeBusca(ArvoreBinariaDeBusca <X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("Modelo ausente.");
        
        this.raiz = ajudaConstrutorDeCopia(this.raiz, modelo.raiz);
    }
    
    private No ajudaConstrutorDeCopia(No r,  No  m)
    {
        if(m == null)
            return null;
        
        r = new No(null, m.getInfo(), null);
        r.setEsq(ajudaConstrutorDeCopia(r.getEsq(), m.getEsq()));
        r.setDir(ajudaConstrutorDeCopia(r.getDir(), m.getDir()));
        
        return r;
    }
    
    @Override
    public Object clone ()
    {
        ArvoreBinariaDeBusca<X> ret=null;

        try
        {
            ret = new ArvoreBinariaDeBusca<>(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
    
    /*
    REMOVA SEM RECURSÃO
    public void remova (X i) throws Exception
    {
        if(i == null)
            throw new Exception("Parametro nulo.");
        
        if(this.raiz == null)
            throw new Exception("Arvore vazia.");
        
        if(i.equals(this.raiz.getInfo()) && this.raiz.getEsq() == null && this.raiz.getDir() == null)
        {
            this.raiz = null;
            return;
        }
        
        if(i.equals(this.raiz.getInfo()) && this.raiz.getEsq() == null && this.raiz.getDir() != null)
        {
            this.raiz = this.raiz.getDir();
            return;
        }
        
        if(i.equals(this.raiz.getInfo()) && this.raiz.getEsq() != null && this.raiz.getDir() == null)
        {
            this.raiz = this.raiz.getEsq( );
            return;
        }
        
        No anterior = null, atual = this.raiz;
        
        for(;;)
        {
            if(atual == null)
                throw new Exception("Informacao nao encontrada.");
    
            int comp = i.compareTo(atual.getInfo());
    
            if(comp == 0)
                break;
    
            if(comp < 0)
            {
                anterior = atual;
                atual = atual.getEsq();
            }else
            {
                anterior = atual;
                atual = atual.getDir();
            }
        }
        
        if(anterior != null && atual.getEsq() == null && atual.getDir() == null)
        {
            if(atual == anterior.getEsq())
                anterior.setEsq(null);
            else
                anterior.setDir(null);
            
            return;
        }
        
        if(anterior != null && atual.getEsq() != null && atual.getDir() == null)
        {
            if(atual == anterior.getEsq())
                anterior.setEsq(atual.getEsq());
            else
                anterior.setDir(atual.getEsq());
            
            return;
        }
    
        if(anterior != null && atual.getEsq() == null && atual.getDir() != null)
        {
            if(atual == anterior.getEsq())
                anterior.setEsq(atual.getDir());
            else
                anterior.setDir(atual.getDir());
            
            return;
        }
    
        No substituto = atual.getEsq();
    
        while(atual.getDir() != null)
        {
            anterior = substituto;
            substituto = substituto.getDir();
        }    
    }*/
}
