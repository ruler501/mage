/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.nemesis;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class AEtherBarrier extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a creature spell");
    static {
            filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public AEtherBarrier(UUID ownerId) {
        super(ownerId, 27, "AEther Barrier", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "NEM";

        // Whenever a player casts a creature spell, that player sacrifices a permanent unless he or she pays {1}.
        this.addAbility(new SpellCastAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new AEtherBarrierEffect(),
                filter,
                false, 
                SetTargetPointer.PLAYER
        ));
    }

    public AEtherBarrier(final AEtherBarrier card) {
        super(card);
    }

    @Override
    public AEtherBarrier copy() {
        return new AEtherBarrier(this);
    }
}

class AEtherBarrierEffect extends SacrificeEffect {
    
    AEtherBarrierEffect() {
        super(new FilterPermanent("permanent to sacrifice"), 1, "that player");
        this.staticText = "that player sacrifices a permanent unless he or she pays {1}";
    }
    
    AEtherBarrierEffect(final AEtherBarrierEffect effect) {
        super(effect);
    }
    
    @Override
    public AEtherBarrierEffect copy() {
        return new AEtherBarrierEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            GenericManaCost cost = new GenericManaCost(1);
            if (!cost.pay(source, game, player.getId(), player.getId(), false)) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}