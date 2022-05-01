from random import sample
from numpy import median, diff

import sys
from aubio import source, tempo, fvec

win_s = 1024                 # fft size
hop_s = win_s // 2          # hop size

if(len(sys.argv) < 4):
    # indicate failure
    sys.exit(1)

#if __name__ == "__main__":
    #print("sick")

filename = sys.argv[1]
analysis_file = filename + sys.argv[2]
analysis_bkup_file = analysis_file + sys.argv[3]

samplerate = 0

s = source(filename, samplerate, hop_s)
samplerate = s.samplerate

o = tempo("specdiff", win_s, hop_s, samplerate)
onsets = []
volume = []
total_frames = 0
def averageEnergy(samples):
    total = 0
    for i in range(0,(3*hop_s//4)):
        total += abs(samples[i])
    return total / ((3*hop_s//4))
while True:
    samples, read = s()
    if o(samples):
        #print("%f" % o.get_last_s())
        onsets.append(o.get_last())
        volume.append(averageEnergy(samples))
    total_frames += read
    if read < hop_s:
        break
from os.path import exists
import shutil
# def beats_to_bpm(beats, path):
#         # if enough beats are found, convert to periods then to bpm
#         if len(beats) > 1:
#             if len(beats) < 4:
#                 print("few beats found in {:s}".format(path))
#             bpms = 60./diff(beats)
#             return median(bpms)
#         else:
#             print("not enough beats found in {:s}".format(path))
#             return 0

# print(beats_to_bpm(onsets, "nrgq"))
file_exists = exists(analysis_file)
if(file_exists):
    shutil.copyfile(analysis_file, analysis_bkup_file)
with open(analysis_file, "w") as file:
    file.write(f"{str(onsets[0])}\t{str(volume[0])}")
    for i in range(1, len(onsets)):
        file.write(f"\n{str(onsets[i])}\t{str(volume[i])}")

sys.exit(0)
