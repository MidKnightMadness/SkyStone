package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

@I2cDeviceType
@DeviceProperties(name = "LED Strip", xmlTag = "LEDStrip")
public class LEDStrip extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    public LEDStrip(I2cDeviceSynch i2cDeviceSynch) {
        super(i2cDeviceSynch, true);
        this.deviceClient.setI2cAddress(I2cAddr.create8bit(0x40));
        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    public void sendHello() {
        byte[] data = "ello".getBytes();
        deviceClient.write8('i', 'i');
    }

    @Override
    protected boolean doInitialize() {
        return true;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Unknown;
    }

    @Override
    public String getDeviceName() {
        return "TestSaleae";
    }
}
