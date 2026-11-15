#!/usr/bin/env node
/*
 * Copy the web game into the app, replacing what is in app/src/main/assets/www.
 *
 *   node tools/sync-web.js                       # uses ../chistan-crossword
 *   node tools/sync-web.js /path/to/chistan-crossword
 *
 * Only the files the game needs at run time are copied: index.html, css/, js/ and fonts/.
 * Needs Node 16.7 or newer. Run it again whenever you change the web game, then rebuild the app.
 */
'use strict';
var fs = require('fs');
var path = require('path');

var root = path.resolve(__dirname, '..');
var source = path.resolve(process.argv[2] || path.join(root, '..', 'chistan-crossword'));
var target = path.join(root, 'app', 'src', 'main', 'assets', 'www');
var ITEMS = ['index.html', 'css', 'js', 'fonts'];

if (!fs.existsSync(path.join(source, 'index.html'))) {
  console.error('No index.html in ' + source + '\nPass the folder of the web game: node tools/sync-web.js /path/to/chistan-crossword');
  process.exit(1);
}
ITEMS.forEach(function (name) {
  if (!fs.existsSync(path.join(source, name))) {
    console.error('Missing "' + name + '" in ' + source);
    process.exit(1);
  }
});

fs.rmSync(target, { recursive: true, force: true });
fs.mkdirSync(target, { recursive: true });
ITEMS.forEach(function (name) {
  fs.cpSync(path.join(source, name), path.join(target, name), { recursive: true });
});

var count = 0;
(function walk(dir) {
  fs.readdirSync(dir, { withFileTypes: true }).forEach(function (e) {
    if (e.isDirectory()) walk(path.join(dir, e.name)); else count++;
  });
})(target);
console.log('Copied ' + count + ' files from ' + source + '\n  to ' + target);
