#! /usr/bin/env python
import nltk, re, pprint, os

def ie_preprocess(document):
	sentences = nltk.sent_tokenize(document)
	sentences = [nltk.word_tokenize(sent) for sent in sentences]
	sentences = [nltk.pos_tag(sent) for sent in sentences]
	return sentences

def process_np(lst):
	print lst
	#for l in lst:
	#	print l[0], ','

def process_ad(lst):
	print lst

def extract(sents):
	global grammar
	global stopwords
	cp = nltk.RegexpParser(grammar)
	for sent in sents:
		tree = cp.parse(sent)
		for subtree in tree.subtrees():
			if subtree.node == 'NP':
				#print subtree
				process_np(subtree.leaves())
			#if subtree.node == 'DOT':
			#	print '---'
		#print ';'
		for subtree in tree.subtrees():
			if subtree.node == 'AD':
				#print subtree
				process_ad(subtree.leaves())
		print '==='


#NP: noun phrases / JJ: adjectives
#transform ',' into ''
#transform upper case start to lower case
#if NP does not contains JJ, then the AD in the same level belongs to NNs in NP
#if NP does not contains JJ, then JJ belongs to NNs in NP

#sometimes you have to find adjs in nouns, since adjs will be classified into nouns sometimes

grammar = r"""
	NP: {<JJ.*>*<NN.*>+<VBG>?}
	AD: {<RB.*>*}
	AD: {<RB.*>?<JJ.*>*}
	DOT: {<,>}
"""

#path_prefix = '/home/victor/workspace_2/crawler/sample'
data_dir = '../sample/hotel_93396'
#data_dir = '../sample/hotel_sample'
stopwords = []
with open('stopwords.txt') as f:
    stopwords = f.read().splitlines()

ids = [100062233, 100163950, 10023367, 100325560, 10071332,
100766141, 10082389, 101056803, 103104840, 103147304, 103370878, 103714696]

cnt = 0
for filename in os.listdir(data_dir):
	if (filename == 'review_%d' % ids[5]):
		f = open('%s/%s' % (data_dir, filename))
		raw = f.read()
		print filename, ":", raw
		extract(ie_preprocess(raw))
		break
	cnt += 1

#print raw