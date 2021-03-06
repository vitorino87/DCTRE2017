package dctre;


import dc.direitoconstitucionaltre2017.R;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.content.SharedPreferences;

public class Questao extends QuestaoConector {
	// vari�vel utilizada para trabalhar com o m�todo checked
	private int z = -1;

	// vari�vel para trabalhar com o m�todo randomizar;
	// private int k;

	// vari�vel para tratar a quantidade de quest�es
	// Essa vari�vel responde � quest�o: Quantas quest�es h� no aplicativo?
	// private final int h = 205;

	// vari�vel para trabalhar com o m�todo embaralhar
	private int[] w = new int[h];

	// Vari�veis para tratar com os objetos da tela2
	TextView tv;
	RadioButton[] rd = new RadioButton[5];
	Button btnRandom, btnChecar, btnBuscar, btnAnterior, btnProximo, btnSortear;
	EditText txtBuscar;

	// vari�veis para impedir que o random repita n�meros
	// private int[] e = new int[h];
	private int f = 0;

	// vari�veis para tratar os R.string, isto �, as strings do programa
	// private int[] a = new int[h];
	// private int[][] b = new int[h][6];

	// m�todo para trabalhar com a tela2
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		z = restaurarEstado();
		w = restaurarW();
		setContentView(R.layout.tela2);
		tv = (TextView) findViewById(R.id.textView1); // ligando os objetos das
														// telas �s vari�veis
		rd[0] = (RadioButton) findViewById(R.id.radio0);
		rd[1] = (RadioButton) findViewById(R.id.radio1);
		rd[2] = (RadioButton) findViewById(R.id.radio2);
		rd[3] = (RadioButton) findViewById(R.id.radio3);
		rd[4] = (RadioButton) findViewById(R.id.radio4);
		btnRandom = (Button) findViewById(R.id.btnRandomizar);
		btnChecar = (Button) findViewById(R.id.btnChecar);
		btnBuscar = (Button) findViewById(R.id.btnBuscar);
		btnAnterior = (Button)findViewById(R.id.btnAnterior);
		btnProximo = (Button)findViewById(R.id.btnProximo);
		btnSortear = (Button)findViewById(R.id.btnSortear);
		txtBuscar = (EditText) findViewById(R.id.editText1);
		if(z!=-1){
			carregarQuestao(z);
			f=z;
		}

		// m�todo para trabalhar com o Sortear
		btnSortear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (f < h) {
					// o array w possui os n�meros das quest�es embaralhadas, a
					// vari�vel f ir� orientar esse array, iniciando de 0 at� 98
					z = w[f];
					carregarQuestao(z);
					f++; // incrementando a vari�vel f que armazena a posi��o do
							// array que est� sendo percorrido.
				}
			}
		});

		// m�todo para trabalhar com o bot�o Checar
		btnChecar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QuestaoAux.checked(z, b, rd, Questao.this);
			}
		});

		// m�todo para trabalhar com o bot�o Buscar
		btnBuscar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				carregarQuestao(Integer.parseInt(txtBuscar.getText().toString()));
			}
		});
		
		//m�todo para trabalhar com o bot�o Anterior
		btnAnterior.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (z > 0) {
					carregarQuestao(--z);
				}
				//carregarQuestao(--z);
			}
		});
		
		//m�todo para trabalhar com o bot�o Pr�ximo
		btnProximo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (z < h) {
					carregarQuestao(++z);
				}
				//carregarQuestao(++z);
			}
		});
		
		//m�todo para trabalhar com o bot�o Random
		btnRandom.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				z=f=0;
				QuestaoAux.embaralhar(w);
				carregarQuestao(w[f]);
			}
		});
	}

	/**
	 * Serve para carregar a quest�o especificada na tela2
	 * 
	 * @param questao
	 */
	public void carregarQuestao(int questao) {
		z = questao;
		tv.setText(a[questao]);
		rd[0].setText(b[questao][0]);
		rd[1].setText(b[questao][1]);
		rd[2].setText(b[questao][2]);
		rd[3].setText(b[questao][3]);
		rd[4].setText(b[questao][4]);
		txtBuscar.setText(String.valueOf(questao));
	}
	/*
	 * Serve para guardar as vari�veis atuais do app
	 * Permitindo que o app continue de onde parou
	 */
	public void guardarEstado(){
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("z", z);
		for(int i=0;i<h;i++){
			String cat="aux"+i;
			editor.putInt(cat, w[i]);
		}
		editor.commit();
	}
	
	/*
	 * Serve para restaurar uma vari�vel guardada pelo m�todo guardarEstado()
	 *  A vari�vel que ele restaura � da �ltima quest�o que foi apresentada na tela
	 */
	public int restaurarEstado(){
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		int defaultValue = -1;
		int value = sharedPref.getInt("z", defaultValue);
		return value;
	}
	
	/*
	 * Serve para restaurar o array guardado pelo m�todo guardarEstado()
	 * O array que ele restaura � o utilizado para fazer o random
	 */
	public int[] restaurarW(){
		int[] aux=new int[h];
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		for(int i=0;i<h;i++){
			int defaultValue = i;
			aux[i]=sharedPref.getInt("aux"+i, defaultValue);
		}
		return aux;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		guardarEstado();
	}
	@Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
	@Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
	@Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
}
