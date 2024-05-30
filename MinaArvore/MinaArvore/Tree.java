import java.lang.reflect.*;
import java.lang.Math;
public class Tree  <X extends Comparable<X>>{
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

    private No raiz;

    public void insira(X info) throws Exception
    {
        if(info == null)
            throw new Exception("Passe uma informação");
        if(this.raiz == null)
        {
            this.raiz = new No(null,info,null);
            return;
        }    
        
        this.insira(info, this.raiz);    
    }

    private void insira(X info, No r) throws Exception
    {
        int comp = info.compareTo(r.getInfo());

        if( comp == 0)
            throw new Exception("Item repetido");
        if(comp > 0)
        {
            if( r.getDir() == null)
            {
                if(info instanceof Cloneable)
                    r.setDir(new No(null,this.meuCloneDeX(info),null));
                else
                    r.setDir(new No(null,info,null));
            return;        
            }

            this.insira(info,r.getDir());
        }
        else
        {
            if( r.getEsq() == null)
            {
                if(info instanceof Cloneable)
                    r.setEsq(new No(null,this.meuCloneDeX(info),null));
                else
                    r.setEsq(new No(null,info,null));
            return;        
            }

            this.insira(info,r.getEsq());
        }
    }

    public void remova(X info) throws Exception
    {
        if(info == null)    
            throw new Exception("Não existe esse item");
        this.remova(null,info, this.raiz);

    }

    public void remova(No a,X info, No r) throws Exception
    {
        if(r == null)
            throw new Exception("O item procurado não consta");

        int comp = info.compareTo(r.getInfo());

            
        if(comp == 0 && r.getEsq() == null && r.getDir() == null )
            {
                if(a == null)
                {
                    this.raiz = null;
                    return;
                }
                if(a.getEsq() == r)
                    a.setEsq(null);
                else
                    a.setDir(null);        
                return;
            }

        if(comp == 0 && r.getEsq() != null && r.getDir() == null )
            {
                if(a == null)
                {
                    this.raiz = r.getEsq();
                    return; 
                }
                    
                if(a.getEsq() == r)
                    a.setEsq(r.getEsq());
                else
                    a.setDir(r.getEsq());        
                
                return;
            }
    
        if(comp == 0 && r.getEsq() == null && r.getDir() != null )
            {
                if(a == null)
                {
                    this.raiz = r.getDir();
                    return; 
                }
                    
                if(a.getEsq() == r)
                    a.setEsq(r.getDir());
                else
                    a.setDir(r.getDir());        
                
                return;
            }

        if( comp == 0)
        {
            No subs = null;
            if(r.getDir().getDir() == null && r.getDir().getEsq() == null)
            {
                r.setInfo(r.getDir().getInfo());
                r.setDir(null); 
                return;
            }

            if(r.getEsq().getDir() == null && r.getEsq().getEsq() == null)
            {
                r.setInfo(r.getEsq().getInfo());
                r.setEsq(null);
                return;
            }

            subs = r.getEsq();

            while(subs.getDir() != null)
            {
                a = subs;
                subs = subs.getDir(); 
            }

            r.setInfo(subs.getInfo());
            this.remova(a,subs.getInfo(), subs);
            return;
        }   
        
        if(comp > 0)
            remova(r,info, r.getDir());
    
        if(comp < 0 )
            remova(r,info, r.getEsq());
    } 

    public String toString(){
        return toString(this.raiz);
    }
public int countLevel(No r, int n,int a)
{
    if(r == null)
        return 0;
    if(n == a)
        return 1;
    return  countLevel(r.getEsq(),n,a+1)+countLevel(r.getDir(),n,a+1);   
}

public boolean balanceada()
{
    return this.balanceada(this.raiz, 0, false);
}

private boolean balanceada(No r, int n, boolean inc)
{
    int countL = this.countLevel(this.raiz,n,0);
    if(countL == 0)
        return true;
    if(countL != Math.pow(2,n))
    {
        if(inc == true)
            return false;
         inc = true;   
    }
        


    return this.balanceada(r, n+1, inc);
}
    private String toString(No r){
        if(r == null)
            return "";
        return "("+this.toString(r.getEsq())+r.getInfo()+ this.toString(r.getDir())+")";
    }
    public Tree ()
    {
        this.raiz = null;
    }

}