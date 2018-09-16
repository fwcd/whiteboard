# Protocol
This document describes the JSON protocol used to communicate between Whiteboard Server and Client using TypeScript declarations. The protocol requires both client and server to maintain state.

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
}
```

### UpdateAllItemsEvent
An event that clears and replaces all items on the client whiteboard. This ensures that the client representation matches the actual whiteboard.

```typescript
interface UpdateAllItemsEvent extends Event {
	name: "updateAllItems";
	items: WhiteboardItem[];
}
```

### AddItemsEvent
An event that adds an item to the client whiteboard. The client should check using the `totalItemCount` whether it notices any discrepancies with its internal whiteboard representation and if so, request all items.

```typescript
interface AddItemsEvent extends Event {
	name: "addItems";
	addedItems: WhiteboardItem[];
	totalItemCount: number;
}
```

### Request
A request is a message sent from a client to the server.

```typescript
interface Request extends Message {
	category: "request";
}
```

### GetAllItemsRequest
Requests an `UpdateAllItemsEvent` from the server.

```typescript
interface GetAllItemsRequest extends Request {}
```

### SetAllItemsRequest
Requests the server to clear and replace all items on the actual whiteboard.

```typescript
interface SetAllItemsRequest extends Request {
	items: WhiteboardItem[];
}
```

### AddItemsRequest
Requests the server to add items to the actual whiteboard.

```typescript
interface AddItemsRequest extends Request {
	addedItems: WhiteboardItem[];
}
```
