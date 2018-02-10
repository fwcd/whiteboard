package com.fwcd.whiteboard.networking.packets;

import java.io.Serializable;

public interface Packet<T> extends Serializable {
	void apply(T model);
}
