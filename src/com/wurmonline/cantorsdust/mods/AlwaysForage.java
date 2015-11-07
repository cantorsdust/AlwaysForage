//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wurmonline.cantorsdust.mods;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmMod;

public class AlwaysForage implements WurmMod, Configurable, PreInitable {
    private Logger _logger = Logger.getLogger(this.getClass().getName());
    private boolean _foragingOn = true;
    private int _foragingOnPower = 1;
    private boolean _botanizingOn = true;
    private int _botanizingOnPower = 1;

    public AlwaysForage() {
    }

    public void configure(Properties properties) {
        this._foragingOn = Boolean.valueOf(properties.getProperty("foragingOn", Boolean.toString(this._foragingOn))).booleanValue();
        this._foragingOnPower = Integer.valueOf(properties.getProperty("foragingOnPower", Integer.toString(this._foragingOnPower))).intValue();
        this.Log("Foraging On: ", this._foragingOn, this._foragingOnPower);
        this._botanizingOn = Boolean.valueOf(properties.getProperty("botanizingOn", Boolean.toString(this._botanizingOn))).booleanValue();
        this._botanizingOnPower = Integer.valueOf(properties.getProperty("botanizingOnPower", Integer.toString(this._botanizingOnPower))).intValue();
        this.Log("Botanizing On: ", this._botanizingOn, this._botanizingOnPower);
    }

    private void Log(String forFeature, boolean activated, int power) {
        this._logger.log(Level.INFO, forFeature + activated + (activated?" for MGMT powers " + power + " and above.":"."));
    }

    public void preInit() {
        if(this._foragingOn) {
            this.ForagingFunction();
        }
        if(this._botanizingOn) {
            this.BotanizingFunction();
        }
    }

    private void ForagingFunction() {
        try {
            CtClass ex = HookManager.getInstance().getClassPool().get("com.wurmonline.server.Server");
            //CtClass[] parameters = new CtClass[]{CtPrimitiveType.intType};
            //CtClass[] parameters = new CtClass[]{HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.TileBehaviour"), CtPrimitiveType.shortType, CtPrimitiveType.floatType, CtPrimitiveType.intType, CtPrimitiveType.booleanType};
            CtClass[] parameters = new CtClass[]{CtPrimitiveType.intType, CtPrimitiveType.intType};
            //CtMethod method = ex.getMethod("isForagable", Descriptor.ofMethod(CtPrimitiveType.booleanType, parameters));
            CtMethod method = ex.getMethod("isForagable", Descriptor.ofMethod(CtPrimitiveType.booleanType, parameters));
            //method.insertBefore("{ if (this.getPower() >= " + this._foragingOnPower + ") return true; }");
            method.insertBefore("return true;");
            method = null;
            parameters = null;
            ex = null;
        } catch (NotFoundException | CannotCompileException var4) {
            throw new HookException(var4);
        }
    }

    private void BotanizingFunction() {
        try {
            CtClass ex = HookManager.getInstance().getClassPool().get("com.wurmonline.server.Server");
            //CtClass[] parameters = new CtClass[]{CtPrimitiveType.intType};
            CtClass[] parameters = new CtClass[]{CtPrimitiveType.intType, CtPrimitiveType.intType};
            //CtMethod method = ex.getMethod("isForagable", Descriptor.ofMethod(CtPrimitiveType.booleanType, parameters));
            CtMethod method = ex.getMethod("isBotanizable", Descriptor.ofMethod(CtPrimitiveType.booleanType, parameters));
            //method.insertBefore("{ if (this.getPower() >= " + this._botanizingOnPower + ") return true; }");
            method.insertBefore("return true;");
            method = null;
            parameters = null;
            ex = null;
        } catch (NotFoundException | CannotCompileException var4) {
            throw new HookException(var4);
        }
    }
}
