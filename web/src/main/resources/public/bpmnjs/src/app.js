import BpmnViewer from 'bpmn-js';
import lintModule from 'bpmn-js-bpmnlint';
import bpmnLintConfig from '../.bpmnlintrc';

window.bpmnViewer = new BpmnViewer({
  container: '#renderedBPMN',
  linting: {
    bpmnlint: bpmnLintConfig
  },
  additionalModules: [
    lintModule
  ]
});