package gameoflife.specs;

import gameoflife.CellLocation;
import gameoflife.Evolution;
import gameoflife.Rule;
import gameoflife.World;
import gameoflife.specs.EvolutionSpec;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.hamcrest.StringDescription;
import org.jnario.lib.Assert;
import org.jnario.lib.JnarioCollectionLiterals;
import org.jnario.lib.Should;
import org.jnario.runner.ExampleGroupRunner;
import org.jnario.runner.Named;
import org.jnario.runner.Order;
import org.junit.Test;
import org.junit.runner.RunWith;

@Named("live cells")
@RunWith(ExampleGroupRunner.class)
@SuppressWarnings("all")
public class EvolutionLiveCellsSpec extends EvolutionSpec {
  final Rule allLiveStayAlive = new Function0<Rule>() {
    public Rule apply() {
      final Rule _function = new Rule() {
          public boolean becomesAlive(final int it) {
            return true;
          }
        };
      return _function;
    }
  }.apply();
  
  @Test
  @Named("stay alive if rule says so")
  @Order(1)
  public void _stayAliveIfRuleSaysSo() throws Exception {
    Evolution _evolution = new Evolution(this.allLiveStayAlive, this.dontCare);
    final Evolution evolution = _evolution;
    World _evolve = evolution.evolve(this.worldWithLiveCell);
    Set<CellLocation> _livingCells = _evolve.getLivingCells();
    Set<CellLocation> _set = JnarioCollectionLiterals.<CellLocation>set(this.livingCell);
    boolean _doubleArrow = Should.<Set<CellLocation>>operator_doubleArrow(_livingCells, _set);
    Assert.assertTrue("\nExpected evolution.evolve(worldWithLiveCell).livingCells => set(livingCell) but"
     + "\n     evolution.evolve(worldWithLiveCell).livingCells is " + new StringDescription().appendValue(_livingCells).toString()
     + "\n     evolution.evolve(worldWithLiveCell) is " + new StringDescription().appendValue(_evolve).toString()
     + "\n     evolution is " + new StringDescription().appendValue(evolution).toString()
     + "\n     worldWithLiveCell is " + new StringDescription().appendValue(this.worldWithLiveCell).toString()
     + "\n     set(livingCell) is " + new StringDescription().appendValue(_set).toString()
     + "\n     livingCell is " + new StringDescription().appendValue(this.livingCell).toString() + "\n", _doubleArrow);
    
  }
}
