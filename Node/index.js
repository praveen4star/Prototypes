const express = require('express');
const app = express();

app.use((req, res, next) => {
	res.setHeader('Access-Control-Allow-Origin', '*');
	res.setHeader('Access-Control-Allow-Methods', 'GET');
	res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
	next();
});

app.get('/', (req, res) => {
	res.send('Hello World');
});

app.get('/events/:id', (req, res) => {
	const id = req.params.id;
	res.setHeader('Content-Type', 'text/event-stream');
	res.setHeader('Cache-Control', 'no-cache');
	res.setHeader('Connection', 'keep-alive');

	setInterval(() => {
		res.write(
			`data: ${JSON.stringify({ time: new Date().toISOString() })}\n\n`
		);
	}, 2000);
});
/** Sending the server side event */
app.post('/events', (req, res) => {
	/** generate random id */
	const id = Math.random().toString(36).substring(2, 15);
	res.setHeader('Content-Type', 'application/json');
	res.send(JSON.stringify({ id }));
});

app.listen(3000, () => {
	console.log('Server is running on port 3000');
});
