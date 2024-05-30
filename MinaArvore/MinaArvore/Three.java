import java.lang.reflect.*;

public class Three{
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

}