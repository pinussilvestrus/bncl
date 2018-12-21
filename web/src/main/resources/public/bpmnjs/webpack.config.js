var path = require('path');

module.exports = {
  mode: 'development',
  entry: './src/app.js',
  output: {
    path: path.resolve(__dirname),
    filename: 'bpmnjs.bundled.js'
  },
  module: {
    rules: [
      {
        test: /\.bpmn$/,
        use: {
          loader: 'raw-loader'
        }
      },
      {
         test: /\.bpmnlintrc$/,
         use: [
           {
             loader: 'bpmnlint-loader',
           }
         ]
      }
    ]
  }
};