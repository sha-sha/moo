package shaul.games.moo.setup;

import shaul.games.moo.model.Ship.Weapon;

/**
 * Created by Shaul on 6/10/2015.
 */
public class ShipWeapons {

    public enum Beam {
        //                            Min Max Rng Shots Halves CarryOver  sz  Pow Cost Bonus
        Laser         (new WeaponBeam(  1,  4,  1,   1,  false,  false),  10,  25,  3,    0),
        HeavyLaser    (new WeaponBeam(  1,  7,  2,   1,  false,  false),  30,  75,  9,    0);

        public final Weapon weapon;
        public final int size;
        public final int power;
        public final int cost;
        public final int hitBonus;

        Beam(Weapon weapon, int size, int power, int cost, int hitBonus) {
            this.weapon = weapon;
            this.size = size;
            this.power = power;
            this.cost = cost;
            this.hitBonus = hitBonus;
        }
    }

    public enum Bomb {
        //                            bombPopReduction
        DeathSpores   (new WeaponBomb(               2),  10,  25,  3);

        public final Weapon weapon;
        public final int size;
        public final int power;
        public final int cost;

        Bomb(Weapon weapon, int size, int power, int cost) {
            this.weapon = weapon;
            this.size = size;
            this.power = power;
            this.cost = cost;
        }
    }

    private static class WeaponBeam extends Weapon {
        public WeaponBeam(int minDamage, int maxDamage, int range, int nomShots,
                          boolean halvesShields, boolean carryOverDamage) {
            super(Type.Beam, minDamage, maxDamage, range, nomShots, halvesShields, carryOverDamage, 0);
        }
    }

    private static class WeaponBomb extends Weapon {
        public WeaponBomb(int bombPopReduction) {
            super(Type.Bomb, 0, 0, 0, 0, false, false, bombPopReduction);
        }
    }

/*
name        tech  dmg(shots)  sz/pow(bon) rng   0    1    2    3    4    5    6    7    9   11   13   15
Laser          1   1- 4(x1)    10/ 25(+0)  1   2.5  1.5  0.7  0.2  --   --   --   --   --   --   --   --
Hvy Laser      1   1- 7(x1)    30/ 75(+0)  2   4.0  3.0  2.1  1.4  0.9  0.4  0.1  --   --   --   --   --
Gat Laser      5   1- 4(x4)    20/ 70(+0)  1  10.0  6.0  3.0  1.0  --   --   --   --   --   --   --   --
^Neut. Pellet  7   2- 5(x1)    15/ 25(+0)  1   3.5  2.5  2.5  1.5  1.5  0.7  0.7  0.2  --   --   --   --
Ion Cannon    10   3- 8(x1)    15/ 35(+0)  1   5.5  4.5  3.5  2.5  1.7  1.0  0.5  0.2  --   --   --   --
Hvy Ion       10   3-15(x1)    45/105(+0)  2   9.0  8.0  7.0  6.0  5.1  4.2  3.5  2.8  1.6  0.8  0.2  --
^Mass Driver  13   5- 8(x1)    55/ 50(+0)  1   6.5  5.5  5.5  4.5  4.5  3.5  3.5  2.5  1.5  0.7  0.2  --
Neutron Blst  15   3-12(x1)    20/ 60(+0)  1   7.5  6.5  5.5  4.5  3.6  2.8  2.1  1.5  0.6  0.1  --   --
Hvy Blast     15   3-24(x1)    60/180(+0)  2  13.5 12.5 11.5 10.5  9.5  8.6  7.8  7.0  5.5  4.1  3.0  2.0
~Graviton     17   1-15(x1)    30/ 60(+0)  1   8.0  7.0  6.1  5.2  4.4  3.7  3.0  2.4  1.4  0.7  0.2  --
^Hard Beam    19   8-12(x1)    50/100(+0)  1  10.0  9.0  9.0  8.0  8.0  7.0  7.0  6.0  5.0  4.0  3.0  2.0
Fusion Beam   20   4-16(x1)    20/ 75(+0)  1  10.0  9.0  8.0  7.0  6.0  5.1  4.2  3.5  2.2  1.2  0.5  0.1
Hvy Fusion    20   4-30(x1)    60/225(+0)  2  17.0 16.0 15.0 14.0 13.0 12.0 11.1 10.2  8.6  7.0  5.7  4.4
Megabolt      25   2-20(x1)    30/ 65(+3)  1  11.0 10.0  9.0  8.1  7.2  6.3  5.5  4.8  3.5  2.4  1.5  0.8
Phasor        26   5-20(x1)    20/ 90(+0)  1  12.5 11.5 10.5  9.5  8.5  7.5  6.6  5.7  4.1  2.8  1.7  0.9
Hvy Phasor    26   5-40(x1)    60/270(+0)  2  22.5 21.5 20.5 19.5 18.5 17.5 16.5 15.6 13.8 12.1 10.5  9.0
Auto Blastor  28   4-16(x3)    30/ 90(+0)  1  30.0 27.0 24.0 21.0 18.0 15.2 12.7 10.4  6.5  3.5  1.4  0.2
~Tachyon Beam 30   1-25(x1)    30/ 80(+0)  1  13.0 12.0 11.0 10.1  9.2  8.4  7.6  6.8  5.4  4.2  3.1  2.2
^Gauss Auto   32   7-10(x4)   105/105(+0)  1  34.0 30.0 30.0 26.0 26.0 22.0 22.0 18.0 14.0 10.0  6.0  3.0
^Particle     33  10-20(x1)    90/ 75(+0)  2  15.0 14.0 14.0 13.0 13.0 12.0 12.0 11.0 10.0  9.0  8.0  7.0
Plasma Canon  35   6-30(x1)    30/110(+0)  1  18.0 17.0 16.0 15.0 14.0 13.0 12.0 11.0  9.2  7.6  6.1  4.8
Death Ray     36 200-1000(x1) 2000/2000(+0)3 600. 599. 598. 597. 596. 595. 594. 593. 591. 589. 587. 585.
Disruptor     37  10-40(x1)    70/160(+0)  2  25.0 24.0 23.0 22.0 21.0 20.0 19.0 18.0 16.0 14.0 12.2 10.5
Pulse Phasor  38   5-20(x3)    40/120(+0)  1  37.5 34.5 31.5 28.5 25.5 22.5 19.7 17.1 12.4  8.4  5.2  2.8
Tri-focus Pl  45  20-50(x1)    70/180(+0)  1  35.0 34.0 33.0 32.0 31.0 30.0 29.0 28.0 26.0 24.0 22.0 20.0
Stellar Conv  46  10-35(x4)   200/300(+0)  3  90.0 86.0 82.0 78.0 74.0 70.0 66.0 62.0 54.0 46.2 38.9 32.3
Mauler Dev    48  20-100(x1)  150/300(+0)  1  60.0 59.0 58.0 57.0 56.0 55.0 54.0 53.0 51.0 49.0 47.0 45.0


 */


}
