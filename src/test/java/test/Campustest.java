package test; 

import br.edu.utfpr.dv.sireata.model.Campus;

import org.junit.jupiter.api.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

class CampusTest {
	private static Campus campus;

  @BeforeClass
  public static void setup() {
    campus.setIdCampus(1);
    campus.setNome("Campus teste");
    campus.setEndereco("Endereco teste");
    campus.setLogo("Logo teste");
    campus.setAtivo(true);
    campus.setSite("Site teste");
  }

  @Test
  public void testIdCampus() {
    assertEquals(1, campus.getIdCampus());
  }

  @Test
  public void testNome() {
    assertEquals("Campus teste", campus.getNome());
  }

  @Test
  public void testEndereco() {
    assertEquals("Edereco teste", campus.getEndereco());
  }

  @Test
  public void testLogo() {
    assertEquals("Logo teste", campus.getLogo());
  }

  @Test
  public void testAtivo() {
    assertEquals(true, campus.getAtivo());
  }

  @Test
  public void testSite() {
    assertEquals("Site teste", campus.getSite());
  }
}