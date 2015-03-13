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
package mage.sets.ravnica;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class HuntedDragon extends CardImpl {

    public HuntedDragon(UUID ownerId) {
        super(ownerId, 131, "Hunted Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // When Hunted Dragon enters the battlefield, put three 2/2 white Knight creature tokens with first strike onto the battlefield under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new KnightToken(), 3), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public HuntedDragon(final HuntedDragon card) {
        super(card);
    }

    @Override
    public HuntedDragon copy() {
        return new HuntedDragon(this);
    }
}

class KnightToken extends Token {
    KnightToken() {
        super("Knight", "2/2 white Knight creature tokens with first strike");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        
        subtype.add("Knight");
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());
    }
}