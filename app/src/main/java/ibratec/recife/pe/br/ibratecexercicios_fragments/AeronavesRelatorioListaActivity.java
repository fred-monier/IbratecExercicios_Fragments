package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AeronavesRelatorioListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_relatorio_lista);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        AeronavesRelatorioListaViewPagerAdapter adapter =
                new AeronavesRelatorioListaViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AeronavesRelatorioListaAsaFixaFragment(), "Asas Fixas");
        adapter.addFragment(new AeronavesRelatorioListaAsaRotativaFragment(), "Asas Rotativas");
        adapter.addFragment(new AeronavesRelatorioListaTodasFragment(), "Todas");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
