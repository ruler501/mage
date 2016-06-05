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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class GateToTheAEther extends CardImpl {

    public GateToTheAEther(UUID ownerId) {
        super(ownerId, 174, "Gate to the AEther", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "MRD";

        // At the beginning of each player's upkeep, that player reveals the top card of his or her library. If it's an artifact, creature, enchantment, or land card, the player may put it onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GateToTheAEtherEffect(), TargetController.ANY, false, true));
    }

    public GateToTheAEther(final GateToTheAEther card) {
        super(card);
    }

    @java.lang.Override
    public GateToTheAEther copy() {
        return new GateToTheAEther(this);
    }
}

class GateToTheAEtherEffect extends OneShotEffect {

    GateToTheAEtherEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "that player reveals the top card of his or her library. If it's an artifact, creature, enchantment, or land card, the player may put it onto the battlefield";
    }

    GateToTheAEtherEffect(final GateToTheAEtherEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public GateToTheAEtherEffect copy() {
        return new GateToTheAEtherEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (activePlayer != null) {
            Card card = activePlayer.getLibrary().getFromTop(game);
            if (card != null) {
                activePlayer.revealCards("Gate to the AEther", new CardsImpl(card), game);
                if (card.getCardType().contains(CardType.ARTIFACT)
                        || card.getCardType().contains(CardType.CREATURE)
                        || card.getCardType().contains(CardType.ENCHANTMENT)
                        || card.getCardType().contains(CardType.LAND)) {
                    if (activePlayer.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                        activePlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
