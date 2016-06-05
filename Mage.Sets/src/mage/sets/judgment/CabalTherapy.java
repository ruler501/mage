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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageObject;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public class CabalTherapy extends CardImpl {

    public CabalTherapy(UUID ownerId) {
        super(ownerId, 62, "Cabal Therapy", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "JUD";

        // Name a nonland card. Target player reveals his or her hand and discards all cards with that name.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CabalTherapyEffect());
        
        // Flashback-Sacrifice a creature.
        this.addAbility(new FlashbackAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,new FilterControlledCreaturePermanent("a creature"), true)), 
                TimingRule.SORCERY));
    }

    public CabalTherapy(final CabalTherapy card) {
        super(card);
    }

    @Override
    public CabalTherapy copy() {
        return new CabalTherapy(this);
    }
}

class CabalTherapyEffect extends OneShotEffect {

    public CabalTherapyEffect() {
        super(Outcome.Discard);
        staticText = "Name a nonland card. Search target player's hand for all cards with that name and discard them";
    }

    public CabalTherapyEffect(final CabalTherapyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (targetPlayer != null && controller != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl(true);
            cardChoice.setMessage("Name a nonland card.");
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();

            while (!controller.choose(Outcome.Discard, cardChoice, game)) {
                if (!controller.canRespond(game)) {
                    return false;
                }
            }

            String cardName = cardChoice.getChoice();
            game.informPlayers(sourceObject.getLogName() + ", named card: [" + cardName + "]");
            for (Card card : targetPlayer.getHand().getCards(game)) {
                if (card.getName().equals(cardName)) {
                    targetPlayer.discard(card, source, game);
                }
            }

            controller.lookAtCards(sourceObject.getName() + " Hand", targetPlayer.getHand(), game);
        }
        return true;
    }

    @Override
    public CabalTherapyEffect copy() {
        return new CabalTherapyEffect(this);
    }
}
