public class Programa
{
	public static void main (String[] args)
	{
		try
		{
			Tree<Double> horarios;
			horarios = new Tree<Double> ();

			horarios.insira(5.0);
			System.out.println(horarios);

			horarios.insira(6.0);
			System.out.println(horarios);


			horarios.insira(4.0);
			System.out.println(horarios);
			horarios.insira(4.5);
			System.out.println(horarios);
			horarios.insira(3.0);
			System.out.println(horarios);
			horarios.insira(2.0);
			System.out.println(horarios);
			horarios.insira(4.8);
			System.out.println(horarios);
			horarios.insira(8.0);
			System.out.println(horarios);
			System.out.println(horarios.balanceada());
		    //System.out.println(horarios.isBalanceada());
		}//
		catch (Exception erro)
		{
			System.err.println (erro);
		}
	}
}