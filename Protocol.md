# Protocol
This document describes the JSON protocol used to communicate between Whiteboard Server and Client using TypeScript declarations. The protocol requires both client and server to maintain state.

## Messages

### Message
Base interface for every method.

```typescript
interface Message {
	category: "event" | "request";
	name: string;
}
```

### Event
An event is a message sent from the server to a client.

```typescript
interface Event extends Message {
	category: "event";
	requester: ClientInfo;
}
```

### UpdateAllItemsEvent
Clears and replaces all items on the client whiteboard. This ensures that the client representation matches the actual whiteboard.

```typescript
interface UpdateAllItemsEvent extends Event {
	name: "updateAllItems";
	items: WhiteboardItem[];
}
```

### AddItemsEvent
Adds an item to the client whiteboard. The client should check using the `totalItemCount` whether it notices any discrepancies with its internal whiteboard representation and if so, request all items.

```typescript
interface AddItemsEvent extends Event {
	name: "addItems";
	addedItems: WhiteboardItem[];
	totalItemCount: number;
}
```

### AddItemPartsEvent
Incrementally adds parts of an unfinished item to the client whiteboard. The client thus should maintain and update a map of all other clients together with their (unfinished) items.

```typescript
interface AddItemPartsEvent extends Event {
	name: "addItemParts";
	addedParts: WhiteboardItem[];
}
```

### ComposePartsEvent
Indicates to the client that the current (unfinished) item by the requester has been completed.

```typescript
interface ComposePartsEvent extends Event {
	name: "composeParts";
}
```

### UpdateDrawPositionEvent
Send an updated draw position (e.g. a mouse coordinate) back to the clients.

```typescript
interface UpdateDrawPositionEvent extends Event {
	name: "updateDrawPosition";
	client: ClientInfo;
	drawPos?: Vec2;
}
```

### Request
A request is a message sent from a client to the server.

```typescript
interface Request extends Message {
	category: "request";
	senderId: number;
}
```

### GetAllItemsRequest
Requests an `UpdateAllItemsEvent` from the server.

```typescript
interface GetAllItemsRequest extends Request {
	name: "getAllItems";
}
```

### SetAllItemsRequest
Requests the server to clear and replace all items on the actual whiteboard.

```typescript
interface SetAllItemsRequest extends Request {
	name: "setAllItems";
	items: WhiteboardItem[];
}
```

### AddItemsRequest
Requests the server to add items to the actual whiteboard.

```typescript
interface AddItemsRequest extends Request {
	name: "addItems";
	addedItems: WhiteboardItem[];
}
```

### AddItemPartsRequest
Notifies the server (and thus other clients) about parts of an incrementally built (unfinished) item.

```typescript
interface AddItemPartsRequest extends Request {
	name: "addItemParts";
	addedParts: WhiteboardItem[];
}
```

### ComposePartsRequest
Indicates to the server (and thus other clients) that the current (unfinished) item has been completed.

```typescript
interface ComposePartsRequest extends Request {
	name: "composeParts";
}
```

### UpdateDrawPositionRequest
Send an updated draw position (e.g. a mouse coordinate) to the server.

```typescript
interface UpdateDrawPositionRequest extends Request {
	name: "updateDrawPosition";
	drawPos?: Vec2;
}
```

### HelloRequest
Informs the server about the client's information upon connecting
to the server.

```typescript
interface HelloRequest extends Request {
	name: "hello";
	info: ClientInfo;
}
```

### DisconnectRequest
Disconnects a client from the server.

```typescript
interface DisconnectRequest extends Request {
	name: "disconnect";
}
```

## Structures
Data structures used to describe domain objects.

### Color
An RGBA color. Each value is described as a number between 0 and 255 (inclusive).

```typescript
interface Color {
	r: number;
	g: number;
	b: number;
	a: number;
}
```

### Vec2
A two-dimensional vector.

```typescript
interface Vec2 {
	x: number;
	y: number;
}
```

### Range
An integer range that includes the start and excludes the end index.

```typescript
interface Range {
	start: number;
	end: number;
}
```

### ClientInfo
An aggregrate of information about a client.

```typescript
interface ClientInfo {
	id: number;
	name: string;
}
```

### WhiteboardItem
A drawable item.

```typescript
interface WhiteboardItem {
	name: string;
}
```

### LineItem
A colored line.

```typescript
interface LineItem extends WhiteboardItem {
	name: "line";
	start: Vec2;
	end: Vec2;
	color: Color;
	thickness: number;
}
```

### PathItem
A colored path.

```typescript
interface PathItem extends WhiteboardItem {
	name: "path";
	vertices: Vec2[];
	color: Color;
	thickness: number;
}
```

### RectItem
A colored rectangle.

```typescript
interface RectItem extends WhiteboardItem {
	name: "rect";
	topLeft: Vec2;
	width: number;
	height: number;
}
```

### TextItem
A colored text field.

```typescript
interface TextItem extends WhiteboardItem {
	name: "text";
	pos: Vec2;
	color: Color;
	size: number;
}
```
