package informationRetrieval;


public class MyRoStemmer extends MyProgram{
	private MyAmong a_0[] = {
            new MyAmong ( "", -1, 3, "", this),
            new MyAmong ( "I", 0, 1, "", this),
            new MyAmong ( "U", 0, 2, "", this)
        };

        private MyAmong a_1[] = {
            new MyAmong ( "ea", -1, 3, "", this),
            new MyAmong ( "a\u0163ia", -1, 7, "", this),
            new MyAmong ( "aua", -1, 2, "", this),
            new MyAmong ( "iua", -1, 4, "", this),
            new MyAmong ( "a\u0163ie", -1, 7, "", this),
            new MyAmong ( "ele", -1, 3, "", this),
            new MyAmong ( "ile", -1, 5, "", this),
            new MyAmong ( "iile", 6, 4, "", this),
            new MyAmong ( "iei", -1, 4, "", this),
            new MyAmong ( "atei", -1, 6, "", this),
            new MyAmong ( "ii", -1, 4, "", this),
            new MyAmong ( "ului", -1, 1, "", this),
            new MyAmong ( "ul", -1, 1, "", this),
            new MyAmong ( "elor", -1, 3, "", this),
            new MyAmong ( "ilor", -1, 4, "", this),
            new MyAmong ( "iilor", 14, 4, "", this)
        };

        private MyAmong a_2[] = {
            new MyAmong ( "icala", -1, 4, "", this),
            new MyAmong ( "iciva", -1, 4, "", this),
            new MyAmong ( "ativa", -1, 5, "", this),
            new MyAmong ( "itiva", -1, 6, "", this),
            new MyAmong ( "icale", -1, 4, "", this),
            new MyAmong ( "a\u0163iune", -1, 5, "", this),
            new MyAmong ( "i\u0163iune", -1, 6, "", this),
            new MyAmong ( "atoare", -1, 5, "", this),
            new MyAmong ( "itoare", -1, 6, "", this),
            new MyAmong ( "\u0103toare", -1, 5, "", this),
            new MyAmong ( "icitate", -1, 4, "", this),
            new MyAmong ( "abilitate", -1, 1, "", this),
            new MyAmong ( "ibilitate", -1, 2, "", this),
            new MyAmong ( "ivitate", -1, 3, "", this),
            new MyAmong ( "icive", -1, 4, "", this),
            new MyAmong ( "ative", -1, 5, "", this),
            new MyAmong ( "itive", -1, 6, "", this),
            new MyAmong ( "icali", -1, 4, "", this),
            new MyAmong ( "atori", -1, 5, "", this),
            new MyAmong ( "icatori", 18, 4, "", this),
            new MyAmong ( "itori", -1, 6, "", this),
            new MyAmong ( "\u0103tori", -1, 5, "", this),
            new MyAmong ( "icitati", -1, 4, "", this),
            new MyAmong ( "abilitati", -1, 1, "", this),
            new MyAmong ( "ivitati", -1, 3, "", this),
            new MyAmong ( "icivi", -1, 4, "", this),
            new MyAmong ( "ativi", -1, 5, "", this),
            new MyAmong ( "itivi", -1, 6, "", this),
            new MyAmong ( "icit\u0103i", -1, 4, "", this),
            new MyAmong ( "abilit\u0103i", -1, 1, "", this),
            new MyAmong ( "ivit\u0103i", -1, 3, "", this),
            new MyAmong ( "icit\u0103\u0163i", -1, 4, "", this),
            new MyAmong ( "abilit\u0103\u0163i", -1, 1, "", this),
            new MyAmong ( "ivit\u0103\u0163i", -1, 3, "", this),
            new MyAmong ( "ical", -1, 4, "", this),
            new MyAmong ( "ator", -1, 5, "", this),
            new MyAmong ( "icator", 35, 4, "", this),
            new MyAmong ( "itor", -1, 6, "", this),
            new MyAmong ( "\u0103tor", -1, 5, "", this),
            new MyAmong ( "iciv", -1, 4, "", this),
            new MyAmong ( "ativ", -1, 5, "", this),
            new MyAmong ( "itiv", -1, 6, "", this),
            new MyAmong ( "ical\u0103", -1, 4, "", this),
            new MyAmong ( "iciv\u0103", -1, 4, "", this),
            new MyAmong ( "ativ\u0103", -1, 5, "", this),
            new MyAmong ( "itiv\u0103", -1, 6, "", this)
        };

        private MyAmong a_3[] = {
            new MyAmong ( "ica", -1, 1, "", this),
            new MyAmong ( "abila", -1, 1, "", this),
            new MyAmong ( "ibila", -1, 1, "", this),
            new MyAmong ( "oasa", -1, 1, "", this),
            new MyAmong ( "ata", -1, 1, "", this),
            new MyAmong ( "ita", -1, 1, "", this),
            new MyAmong ( "anta", -1, 1, "", this),
            new MyAmong ( "ista", -1, 3, "", this),
            new MyAmong ( "uta", -1, 1, "", this),
            new MyAmong ( "iva", -1, 1, "", this),
            new MyAmong ( "ic", -1, 1, "", this),
            new MyAmong ( "ice", -1, 1, "", this),
            new MyAmong ( "abile", -1, 1, "", this),
            new MyAmong ( "ibile", -1, 1, "", this),
            new MyAmong ( "isme", -1, 3, "", this),
            new MyAmong ( "iune", -1, 2, "", this),
            new MyAmong ( "oase", -1, 1, "", this),
            new MyAmong ( "ate", -1, 1, "", this),
            new MyAmong ( "itate", 17, 1, "", this),
            new MyAmong ( "ite", -1, 1, "", this),
            new MyAmong ( "ante", -1, 1, "", this),
            new MyAmong ( "iste", -1, 3, "", this),
            new MyAmong ( "ute", -1, 1, "", this),
            new MyAmong ( "ive", -1, 1, "", this),
            new MyAmong ( "ici", -1, 1, "", this),
            new MyAmong ( "abili", -1, 1, "", this),
            new MyAmong ( "ibili", -1, 1, "", this),
            new MyAmong ( "iuni", -1, 2, "", this),
            new MyAmong ( "atori", -1, 1, "", this),
            new MyAmong ( "osi", -1, 1, "", this),
            new MyAmong ( "ati", -1, 1, "", this),
            new MyAmong ( "itati", 30, 1, "", this),
            new MyAmong ( "iti", -1, 1, "", this),
            new MyAmong ( "anti", -1, 1, "", this),
            new MyAmong ( "isti", -1, 3, "", this),
            new MyAmong ( "uti", -1, 1, "", this),
            new MyAmong ( "i\u015Fti", -1, 3, "", this),
            new MyAmong ( "ivi", -1, 1, "", this),
            new MyAmong ( "it\u0103i", -1, 1, "", this),
            new MyAmong ( "o\u015Fi", -1, 1, "", this),
            new MyAmong ( "it\u0103\u0163i", -1, 1, "", this),
            new MyAmong ( "abil", -1, 1, "", this),
            new MyAmong ( "ibil", -1, 1, "", this),
            new MyAmong ( "ism", -1, 3, "", this),
            new MyAmong ( "ator", -1, 1, "", this),
            new MyAmong ( "os", -1, 1, "", this),
            new MyAmong ( "at", -1, 1, "", this),
            new MyAmong ( "it", -1, 1, "", this),
            new MyAmong ( "ant", -1, 1, "", this),
            new MyAmong ( "ist", -1, 3, "", this),
            new MyAmong ( "ut", -1, 1, "", this),
            new MyAmong ( "iv", -1, 1, "", this),
            new MyAmong ( "ic\u0103", -1, 1, "", this),
            new MyAmong ( "abil\u0103", -1, 1, "", this),
            new MyAmong ( "ibil\u0103", -1, 1, "", this),
            new MyAmong ( "oas\u0103", -1, 1, "", this),
            new MyAmong ( "at\u0103", -1, 1, "", this),
            new MyAmong ( "it\u0103", -1, 1, "", this),
            new MyAmong ( "ant\u0103", -1, 1, "", this),
            new MyAmong ( "ist\u0103", -1, 3, "", this),
            new MyAmong ( "ut\u0103", -1, 1, "", this),
            new MyAmong ( "iv\u0103", -1, 1, "", this)
        };

        private MyAmong a_4[] = {
            new MyAmong ( "ea", -1, 1, "", this),
            new MyAmong ( "ia", -1, 1, "", this),
            new MyAmong ( "esc", -1, 1, "", this),
            new MyAmong ( "\u0103sc", -1, 1, "", this),
            new MyAmong ( "ind", -1, 1, "", this),
            new MyAmong ( "\u00E2nd", -1, 1, "", this),
            new MyAmong ( "are", -1, 1, "", this),
            new MyAmong ( "ere", -1, 1, "", this),
            new MyAmong ( "ire", -1, 1, "", this),
            new MyAmong ( "\u00E2re", -1, 1, "", this),
            new MyAmong ( "se", -1, 2, "", this),
            new MyAmong ( "ase", 10, 1, "", this),
            new MyAmong ( "sese", 10, 2, "", this),
            new MyAmong ( "ise", 10, 1, "", this),
            new MyAmong ( "use", 10, 1, "", this),
            new MyAmong ( "\u00E2se", 10, 1, "", this),
            new MyAmong ( "e\u015Fte", -1, 1, "", this),
            new MyAmong ( "\u0103\u015Fte", -1, 1, "", this),
            new MyAmong ( "eze", -1, 1, "", this),
            new MyAmong ( "ai", -1, 1, "", this),
            new MyAmong ( "eai", 19, 1, "", this),
            new MyAmong ( "iai", 19, 1, "", this),
            new MyAmong ( "sei", -1, 2, "", this),
            new MyAmong ( "e\u015Fti", -1, 1, "", this),
            new MyAmong ( "\u0103\u015Fti", -1, 1, "", this),
            new MyAmong ( "ui", -1, 1, "", this),
            new MyAmong ( "ezi", -1, 1, "", this),
            new MyAmong ( "\u00E2i", -1, 1, "", this),
            new MyAmong ( "a\u015Fi", -1, 1, "", this),
            new MyAmong ( "se\u015Fi", -1, 2, "", this),
            new MyAmong ( "ase\u015Fi", 29, 1, "", this),
            new MyAmong ( "sese\u015Fi", 29, 2, "", this),
            new MyAmong ( "ise\u015Fi", 29, 1, "", this),
            new MyAmong ( "use\u015Fi", 29, 1, "", this),
            new MyAmong ( "\u00E2se\u015Fi", 29, 1, "", this),
            new MyAmong ( "i\u015Fi", -1, 1, "", this),
            new MyAmong ( "u\u015Fi", -1, 1, "", this),
            new MyAmong ( "\u00E2\u015Fi", -1, 1, "", this),
            new MyAmong ( "a\u0163i", -1, 2, "", this),
            new MyAmong ( "ea\u0163i", 38, 1, "", this),
            new MyAmong ( "ia\u0163i", 38, 1, "", this),
            new MyAmong ( "e\u0163i", -1, 2, "", this),
            new MyAmong ( "i\u0163i", -1, 2, "", this),
            new MyAmong ( "\u00E2\u0163i", -1, 2, "", this),
            new MyAmong ( "ar\u0103\u0163i", -1, 1, "", this),
            new MyAmong ( "ser\u0103\u0163i", -1, 2, "", this),
            new MyAmong ( "aser\u0103\u0163i", 45, 1, "", this),
            new MyAmong ( "seser\u0103\u0163i", 45, 2, "", this),
            new MyAmong ( "iser\u0103\u0163i", 45, 1, "", this),
            new MyAmong ( "user\u0103\u0163i", 45, 1, "", this),
            new MyAmong ( "\u00E2ser\u0103\u0163i", 45, 1, "", this),
            new MyAmong ( "ir\u0103\u0163i", -1, 1, "", this),
            new MyAmong ( "ur\u0103\u0163i", -1, 1, "", this),
            new MyAmong ( "\u00E2r\u0103\u0163i", -1, 1, "", this),
            new MyAmong ( "am", -1, 1, "", this),
            new MyAmong ( "eam", 54, 1, "", this),
            new MyAmong ( "iam", 54, 1, "", this),
            new MyAmong ( "em", -1, 2, "", this),
            new MyAmong ( "asem", 57, 1, "", this),
            new MyAmong ( "sesem", 57, 2, "", this),
            new MyAmong ( "isem", 57, 1, "", this),
            new MyAmong ( "usem", 57, 1, "", this),
            new MyAmong ( "\u00E2sem", 57, 1, "", this),
            new MyAmong ( "im", -1, 2, "", this),
            new MyAmong ( "\u00E2m", -1, 2, "", this),
            new MyAmong ( "\u0103m", -1, 2, "", this),
            new MyAmong ( "ar\u0103m", 65, 1, "", this),
            new MyAmong ( "ser\u0103m", 65, 2, "", this),
            new MyAmong ( "aser\u0103m", 67, 1, "", this),
            new MyAmong ( "seser\u0103m", 67, 2, "", this),
            new MyAmong ( "iser\u0103m", 67, 1, "", this),
            new MyAmong ( "user\u0103m", 67, 1, "", this),
            new MyAmong ( "\u00E2ser\u0103m", 67, 1, "", this),
            new MyAmong ( "ir\u0103m", 65, 1, "", this),
            new MyAmong ( "ur\u0103m", 65, 1, "", this),
            new MyAmong ( "\u00E2r\u0103m", 65, 1, "", this),
            new MyAmong ( "au", -1, 1, "", this),
            new MyAmong ( "eau", 76, 1, "", this),
            new MyAmong ( "iau", 76, 1, "", this),
            new MyAmong ( "indu", -1, 1, "", this),
            new MyAmong ( "\u00E2ndu", -1, 1, "", this),
            new MyAmong ( "ez", -1, 1, "", this),
            new MyAmong ( "easc\u0103", -1, 1, "", this),
            new MyAmong ( "ar\u0103", -1, 1, "", this),
            new MyAmong ( "ser\u0103", -1, 2, "", this),
            new MyAmong ( "aser\u0103", 84, 1, "", this),
            new MyAmong ( "seser\u0103", 84, 2, "", this),
            new MyAmong ( "iser\u0103", 84, 1, "", this),
            new MyAmong ( "user\u0103", 84, 1, "", this),
            new MyAmong ( "\u00E2ser\u0103", 84, 1, "", this),
            new MyAmong ( "ir\u0103", -1, 1, "", this),
            new MyAmong ( "ur\u0103", -1, 1, "", this),
            new MyAmong ( "\u00E2r\u0103", -1, 1, "", this),
            new MyAmong ( "eaz\u0103", -1, 1, "", this)
        };

        private MyAmong a_5[] = {
            new MyAmong ( "a", -1, 1, "", this),
            new MyAmong ( "e", -1, 1, "", this),
            new MyAmong ( "ie", 1, 1, "", this),
            new MyAmong ( "i", -1, 1, "", this),
            new MyAmong ( "\u0103", -1, 1, "", this)
        };

        private static final char g_v[] = {17, 65, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 32, 0, 0, 4 };

        private boolean B_standard_suffix_removed;
        private int I_p2;
        private int I_p1;
        private int I_pV;

//        private void copy_from(MyRoStemmer other) {
//            B_standard_suffix_removed = other.B_standard_suffix_removed;
//            I_p2 = other.I_p2;
//            I_p1 = other.I_p1;
//            I_pV = other.I_pV;
//            super.copy_from(other);
//        }

        private boolean r_prelude() {
            int v_1;
            int v_2;
            int v_3;
            // (, line 31
            // repeat, line 32
            replab0: while(true)
            {
                v_1 = cursor;
                lab1: do {
                    // goto, line 32
                    golab2: while(true)
                    {
                        v_2 = cursor;
                        lab3: do {
                            // (, line 32
                            if (!(in_grouping(g_v, 97, 259)))
                            {
                                break lab3;
                            }
                            // [, line 33
                            bra = cursor;
                            // or, line 33
                            lab4: do {
                                v_3 = cursor;
                                lab5: do {
                                    // (, line 33
                                    // literal, line 33
                                    if (!(eq_s(1, "u")))
                                    {
                                        break lab5;
                                    }
                                    // ], line 33
                                    ket = cursor;
                                    if (!(in_grouping(g_v, 97, 259)))
                                    {
                                        break lab5;
                                    }
                                    // <-, line 33
                                    slice_from("U");
                                    break lab4;
                                } while (false);
                                cursor = v_3;
                                // (, line 34
                                // literal, line 34
                                if (!(eq_s(1, "i")))
                                {
                                    break lab3;
                                }
                                // ], line 34
                                ket = cursor;
                                if (!(in_grouping(g_v, 97, 259)))
                                {
                                    break lab3;
                                }
                                // <-, line 34
                                slice_from("I");
                            } while (false);
                            cursor = v_2;
                            break golab2;
                        } while (false);
                        cursor = v_2;
                        if (cursor >= limit)
                        {
                            break lab1;
                        }
                        cursor++;
                    }
                    continue replab0;
                } while (false);
                cursor = v_1;
                break replab0;
            }
            return true;
        }

        private boolean r_mark_regions() {
            int v_1;
            int v_2;
            int v_3;
            int v_6;
            int v_8;
            // (, line 38
            I_pV = limit;
            I_p1 = limit;
            I_p2 = limit;
            // do, line 44
            v_1 = cursor;
            lab0: do {
                // (, line 44
                // or, line 46
                lab1: do {
                    v_2 = cursor;
                    lab2: do {
                        // (, line 45
                        if (!(in_grouping(g_v, 97, 259)))
                        {
                            break lab2;
                        }
                        // or, line 45
                        lab3: do {
                            v_3 = cursor;
                            lab4: do {
                                // (, line 45
                                if (!(out_grouping(g_v, 97, 259)))
                                {
                                    break lab4;
                                }
                                // gopast, line 45
                                golab5: while(true)
                                {
                                    lab6: do {
                                        if (!(in_grouping(g_v, 97, 259)))
                                        {
                                            break lab6;
                                        }
                                        break golab5;
                                    } while (false);
                                    if (cursor >= limit)
                                    {
                                        break lab4;
                                    }
                                    cursor++;
                                }
                                break lab3;
                            } while (false);
                            cursor = v_3;
                            // (, line 45
                            if (!(in_grouping(g_v, 97, 259)))
                            {
                                break lab2;
                            }
                            // gopast, line 45
                            golab7: while(true)
                            {
                                lab8: do {
                                    if (!(out_grouping(g_v, 97, 259)))
                                    {
                                        break lab8;
                                    }
                                    break golab7;
                                } while (false);
                                if (cursor >= limit)
                                {
                                    break lab2;
                                }
                                cursor++;
                            }
                        } while (false);
                        break lab1;
                    } while (false);
                    cursor = v_2;
                    // (, line 47
                    if (!(out_grouping(g_v, 97, 259)))
                    {
                        break lab0;
                    }
                    // or, line 47
                    lab9: do {
                        v_6 = cursor;
                        lab10: do {
                            // (, line 47
                            if (!(out_grouping(g_v, 97, 259)))
                            {
                                break lab10;
                            }
                            // gopast, line 47
                            golab11: while(true)
                            {
                                lab12: do {
                                    if (!(in_grouping(g_v, 97, 259)))
                                    {
                                        break lab12;
                                    }
                                    break golab11;
                                } while (false);
                                if (cursor >= limit)
                                {
                                    break lab10;
                                }
                                cursor++;
                            }
                            break lab9;
                        } while (false);
                        cursor = v_6;
                        // (, line 47
                        if (!(in_grouping(g_v, 97, 259)))
                        {
                            break lab0;
                        }
                        // next, line 47
                        if (cursor >= limit)
                        {
                            break lab0;
                        }
                        cursor++;
                    } while (false);
                } while (false);
                // setmark pV, line 48
                I_pV = cursor;
            } while (false);
            cursor = v_1;
            // do, line 50
            v_8 = cursor;
            lab13: do {
                // (, line 50
                // gopast, line 51
                golab14: while(true)
                {
                    lab15: do {
                        if (!(in_grouping(g_v, 97, 259)))
                        {
                            break lab15;
                        }
                        break golab14;
                    } while (false);
                    if (cursor >= limit)
                    {
                        break lab13;
                    }
                    cursor++;
                }
                // gopast, line 51
                golab16: while(true)
                {
                    lab17: do {
                        if (!(out_grouping(g_v, 97, 259)))
                        {
                            break lab17;
                        }
                        break golab16;
                    } while (false);
                    if (cursor >= limit)
                    {
                        break lab13;
                    }
                    cursor++;
                }
                // setmark p1, line 51
                I_p1 = cursor;
                // gopast, line 52
                golab18: while(true)
                {
                    lab19: do {
                        if (!(in_grouping(g_v, 97, 259)))
                        {
                            break lab19;
                        }
                        break golab18;
                    } while (false);
                    if (cursor >= limit)
                    {
                        break lab13;
                    }
                    cursor++;
                }
                // gopast, line 52
                golab20: while(true)
                {
                    lab21: do {
                        if (!(out_grouping(g_v, 97, 259)))
                        {
                            break lab21;
                        }
                        break golab20;
                    } while (false);
                    if (cursor >= limit)
                    {
                        break lab13;
                    }
                    cursor++;
                }
                // setmark p2, line 52
                I_p2 = cursor;
            } while (false);
            cursor = v_8;
            return true;
        }

        private boolean r_postlude() {
            int MyAmong_var;
            int v_1;
            // repeat, line 56
            replab0: while(true)
            {
                v_1 = cursor;
                lab1: do {
                    // (, line 56
                    // [, line 58
                    bra = cursor;
                    // substring, line 58
                    MyAmong_var = find_among(a_0, 3);
                    if (MyAmong_var == 0)
                    {
                        break lab1;
                    }
                    // ], line 58
                    ket = cursor;
                    switch(MyAmong_var) {
                        case 0:
                            break lab1;
                        case 1:
                            // (, line 59
                            // <-, line 59
                            slice_from("i");
                            break;
                        case 2:
                            // (, line 60
                            // <-, line 60
                            slice_from("u");
                            break;
                        case 3:
                            // (, line 61
                            // next, line 61
                            if (cursor >= limit)
                            {
                                break lab1;
                            }
                            cursor++;
                            break;
                    }
                    continue replab0;
                } while (false);
                cursor = v_1;
                break replab0;
            }
            return true;
        }

        private boolean r_RV() {
            if (!(I_pV <= cursor))
            {
                return false;
            }
            return true;
        }

        private boolean r_R1() {
            if (!(I_p1 <= cursor))
            {
                return false;
            }
            return true;
        }

        private boolean r_R2() {
            if (!(I_p2 <= cursor))
            {
                return false;
            }
            return true;
        }

        private boolean r_step_0() {
            int MyAmong_var;
            int v_1;
            // (, line 72
            // [, line 73
            ket = cursor;
            // substring, line 73
            MyAmong_var = find_among_b(a_1, 16);
            if (MyAmong_var == 0)
            {
                return false;
            }
            // ], line 73
            bra = cursor;
            // call R1, line 73
            if (!r_R1())
            {
                return false;
            }
            switch(MyAmong_var) {
                case 0:
                    return false;
                case 1:
                    // (, line 75
                    // delete, line 75
                    slice_del();
                    break;
                case 2:
                    // (, line 77
                    // <-, line 77
                    slice_from("a");
                    break;
                case 3:
                    // (, line 79
                    // <-, line 79
                    slice_from("e");
                    break;
                case 4:
                    // (, line 81
                    // <-, line 81
                    slice_from("i");
                    break;
                case 5:
                    // (, line 83
                    // not, line 83
                    {
                        v_1 = limit - cursor;
                        lab0: do {
                            // literal, line 83
                            if (!(eq_s_b(2, "ab")))
                            {
                                break lab0;
                            }
                            return false;
                        } while (false);
                        cursor = limit - v_1;
                    }
                    // <-, line 83
                    slice_from("i");
                    break;
                case 6:
                    // (, line 85
                    // <-, line 85
                    slice_from("at");
                    break;
                case 7:
                    // (, line 87
                    // <-, line 87
                    slice_from("a\u0163i");
                    break;
            }
            return true;
        }

        private boolean r_combo_suffix() {
            int MyAmong_var;
            int v_1;
            // test, line 91
            v_1 = limit - cursor;
            // (, line 91
            // [, line 92
            ket = cursor;
            // substring, line 92
            MyAmong_var = find_among_b(a_2, 46);
            if (MyAmong_var == 0)
            {
                return false;
            }
            // ], line 92
            bra = cursor;
            // call R1, line 92
            if (!r_R1())
            {
                return false;
            }
            // (, line 92
            switch(MyAmong_var) {
                case 0:
                    return false;
                case 1:
                    // (, line 100
                    // <-, line 101
                    slice_from("abil");
                    break;
                case 2:
                    // (, line 103
                    // <-, line 104
                    slice_from("ibil");
                    break;
                case 3:
                    // (, line 106
                    // <-, line 107
                    slice_from("iv");
                    break;
                case 4:
                    // (, line 112
                    // <-, line 113
                    slice_from("ic");
                    break;
                case 5:
                    // (, line 117
                    // <-, line 118
                    slice_from("at");
                    break;
                case 6:
                    // (, line 121
                    // <-, line 122
                    slice_from("it");
                    break;
            }
            // set standard_suffix_removed, line 125
            B_standard_suffix_removed = true;
            cursor = limit - v_1;
            return true;
        }

        private boolean r_standard_suffix() {
            int MyAmong_var;
            int v_1;
            // (, line 129
            // unset standard_suffix_removed, line 130
            B_standard_suffix_removed = false;
            // repeat, line 131
            replab0: while(true)
            {
                v_1 = limit - cursor;
                lab1: do {
                    // call combo_suffix, line 131
                    if (!r_combo_suffix())
                    {
                        break lab1;
                    }
                    continue replab0;
                } while (false);
                cursor = limit - v_1;
                break replab0;
            }
            // [, line 132
            ket = cursor;
            // substring, line 132
            MyAmong_var = find_among_b(a_3, 62);
            if (MyAmong_var == 0)
            {
                return false;
            }
            // ], line 132
            bra = cursor;
            // call R2, line 132
            if (!r_R2())
            {
                return false;
            }
            // (, line 132
            switch(MyAmong_var) {
                case 0:
                    return false;
                case 1:
                    // (, line 148
                    // delete, line 149
                    slice_del();
                    break;
                case 2:
                    // (, line 151
                    // literal, line 152
                    if (!(eq_s_b(1, "\u0163")))
                    {
                        return false;
                    }
                    // ], line 152
                    bra = cursor;
                    // <-, line 152
                    slice_from("t");
                    break;
                case 3:
                    // (, line 155
                    // <-, line 156
                    slice_from("ist");
                    break;
            }
            // set standard_suffix_removed, line 160
            B_standard_suffix_removed = true;
            return true;
        }

        private boolean r_verb_suffix() {
            int MyAmong_var;
            int v_1;
            int v_2;
            int v_3;
            // setlimit, line 164
            v_1 = limit - cursor;
            // tomark, line 164
            if (cursor < I_pV)
            {
                return false;
            }
            cursor = I_pV;
            v_2 = limit_backward;
            limit_backward = cursor;
            cursor = limit - v_1;
            // (, line 164
            // [, line 165
            ket = cursor;
            // substring, line 165
            MyAmong_var = find_among_b(a_4, 94);
            if (MyAmong_var == 0)
            {
                limit_backward = v_2;
                return false;
            }
            // ], line 165
            bra = cursor;
            switch(MyAmong_var) {
                case 0:
                    limit_backward = v_2;
                    return false;
                case 1:
                    // (, line 200
                    // or, line 200
                    lab0: do {
                        v_3 = limit - cursor;
                        lab1: do {
                            if (!(out_grouping_b(g_v, 97, 259)))
                            {
                                break lab1;
                            }
                            break lab0;
                        } while (false);
                        cursor = limit - v_3;
                        // literal, line 200
                        if (!(eq_s_b(1, "u")))
                        {
                            limit_backward = v_2;
                            return false;
                        }
                    } while (false);
                    // delete, line 200
                    slice_del();
                    break;
                case 2:
                    // (, line 214
                    // delete, line 214
                    slice_del();
                    break;
            }
            limit_backward = v_2;
            return true;
        }

        private boolean r_vowel_suffix() {
            int MyAmong_var;
            // (, line 218
            // [, line 219
            ket = cursor;
            // substring, line 219
            MyAmong_var = find_among_b(a_5, 5);
            if (MyAmong_var == 0)
            {
                return false;
            }
            // ], line 219
            bra = cursor;
            // call RV, line 219
            if (!r_RV())
            {
                return false;
            }
            switch(MyAmong_var) {
                case 0:
                    return false;
                case 1:
                    // (, line 220
                    // delete, line 220
                    slice_del();
                    break;
            }
            return true;
        }

        private boolean isStemmable( String term )
        {
        	for ( int c = 0; c < term.length(); c++ ) 
        	{
        		if ( !Character.isLetter( term.charAt( c ) ) ) return false;
        	}
        	return true;
        }        
        private void substitute(StringBuilder sb)
    	{
        	int lit;
        	
        	for(int c=0;c<sb.length();c++)
        	{
        		lit=sb.charAt(c);
        		// LATIN SMALL LETTER S WITH CEDILLA
        		if (lit == '\u015f') sb.setCharAt(c, 's');
        		// LATIN SMALL LETTER T WITH CEDILLA
        		if (lit == '\u0163') sb.setCharAt(c, 't');
        		// LATIN SMALL LETTER A WITH BREVE
        		if (lit == '\u0103') sb.setCharAt(c, 'a');
        		// LATIN SMALL LETTER A WITH CIRCUMFLEX
        		if (lit == '\u00e2') sb.setCharAt(c, 'a');
        		// LATIN SMALL LETTER I WITH CIRCUMFLEX
        		if (lit == '\u00ee') sb.setCharAt(c, 'i');
        	}
        }
        public void stemDiacritics()
        {
        	String term = current.toString();
        	StringBuilder sb = current;
    		if ( isStemmable( term ) )
    		{
    		// Reset the StringBuilder
    		sb.delete( 0, sb.length() );
    		sb.insert( 0, term );
    		
    		// Stemming starts here...
    		substitute( sb );
    		}
        }
        public boolean stem() {
        	
            int v_1;
            int v_2;
            int v_3;
            int v_4;
            int v_5;
            int v_6;
            int v_7;
            int v_8;
            stemDiacritics();
            // (, line 225
            // do, line 226
            v_1 = cursor;
            lab0: do {
                // call prelude, line 226
                if (!r_prelude())
                {
                    break lab0;
                }
            } while (false);
            cursor = v_1;
            // do, line 227
            v_2 = cursor;
            lab1: do {
                // call mark_regions, line 227
                if (!r_mark_regions())
                {
                    break lab1;
                }
            } while (false);
            cursor = v_2;
            // backwards, line 228
            limit_backward = cursor; cursor = limit;
            // (, line 228
            // do, line 229
            v_3 = limit - cursor;
            lab2: do {
                // call step_0, line 229
                if (!r_step_0())
                {
                    break lab2;
                }
            } while (false);
            cursor = limit - v_3;
            // do, line 230
            v_4 = limit - cursor;
            lab3: do {
                // call standard_suffix, line 230
                if (!r_standard_suffix())
                {
                    break lab3;
                }
            } while (false);
            cursor = limit - v_4;
            // do, line 231
            v_5 = limit - cursor;
            lab4: do {
                // (, line 231
                // or, line 231
                lab5: do {
                    v_6 = limit - cursor;
                    lab6: do {
                        // Boolean test standard_suffix_removed, line 231
                        if (!(B_standard_suffix_removed))
                        {
                            break lab6;
                        }
                        break lab5;
                    } while (false);
                    cursor = limit - v_6;
                    // call verb_suffix, line 231
                    if (!r_verb_suffix())
                    {
                        break lab4;
                    }
                } while (false);
            } while (false);
            cursor = limit - v_5;
            // do, line 232
            v_7 = limit - cursor;
            lab7: do {
                // call vowel_suffix, line 232
                if (!r_vowel_suffix())
                {
                    break lab7;
                }
            } while (false);
            cursor = limit - v_7;
            cursor = limit_backward;            // do, line 234
            v_8 = cursor;
            lab8: do {
                // call postlude, line 234
                if (!r_postlude())
                {
                    break lab8;
                }
            } while (false);
            cursor = v_8;
          return true;
        }

}
