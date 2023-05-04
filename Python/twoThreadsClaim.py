#### HASSAN SHABIR ####
#### MASTER OF COMPUTER SCIENCE AND NETWORKKING ###
#### SESSION 2021-2022 ###

import functools  
import statistics 
import threading   
import time       
import os          

# here i am creating output_files folder 
if not os.path.exists("./output_files"):
    os.makedirs("./output_files")


# decorator function for benchmarking
def bench(fun, n_threads=1, seq_iter=1, iter=1):
    @functools.wraps(fun)  
    def wrapper(*args):

        # Function that each thread invoke when it is executed
        def thread_function():
            for _ in range(seq_iter):
                fun(*args)

        running_times = []  

        # execute the function multiple times and collect running times
        for i in range(iter):
            thread_list = []
            for j in range(n_threads):
                thread = threading.Thread(target=thread_function)
                thread_list.append(thread)

            start_time = time.perf_counter()  # start timing

            # start all threads
            for thread in thread_list:
                thread.start()
           

            # wait for all threads to finish
            for thread in thread_list:
                thread.join()
            end_time = time.perf_counter()  

            
            running_times.append(end_time - start_time)

        # calculate the mean and variance of running times
        mean = statistics.mean(running_times)
        if len(running_times) >= 2:
            variance = statistics.variance(running_times, mean)
        else:
            variance = float('nan')

        # return a dictionary with the collected data
        return {
            "fun": fun.__name__,
            "args": args,
            "n_threads": n_threads,
            "seq_iter": seq_iter,
            "iter": iter,
            "mean": mean,
            "variance": variance
        }

    return wrapper


# sample functions to be tested
def just_wait(n):
    time.sleep(n * 0.1)


def grezzo(n):
    for _ in range(2 ** n):
        pass

# function to test a given function with multiple thread and sequence iteration combinations
def test(iter, fun, *args):
    for n_threads, seq_iter in [(1, 16), (2, 8), (4, 4), (8, 2)]:
            # benchmark the given function with the given parameters
            benchmarked = bench(fun, n_threads, seq_iter, iter)(*args)
            # generate a filename based on the function name and parameters
            filename = f"./output_files/{fun.__name__}_{args}_{n_threads}_{seq_iter}.txt"
            # write the collected data to the file
            with open(filename, "w") as f:
                f.write(f"Function name: {benchmarked['fun']}\n")
                f.write(f"Args: {benchmarked['args']}\n")
                f.write(f"Number of threads: {benchmarked['n_threads']}\n")
                f.write(f"seq_iter: {benchmarked['seq_iter']}\n")
                f.write(f"iter: {benchmarked['iter']}\n")
                f.write(f"Mean: = {benchmarked['mean']}\n")
                f.write(f"Variance: = {benchmarked['variance']}\n")
                f.close()


test(4, grezzo, 25)
test(4, just_wait, 25)


"""

From my testing, I found that the performance of multithreading in Python depends on the nature of the computation.
In CPU-bound computations, multithreading does not necessarily lead to better performance since the CPU can only be used by one thread at a time.
For instance, when running the function grezzo with multiple threads, I observed that having more threads can sometimes degrade performance most of times.

On the other hand, in IO CPU-bound computations, multithreading can significantly improve performance.
For example, when running the function just_wait with multiple threads, I observed that doubling the number of threads halved the execution time.

Therefore, the claim that "two threads calling a function may take twice as much time as a single thread calling the function twice" is not 
always true.It depends on the nature of the computation. In IO Based computations, multithreading can reduce execution time, while in CPU-bound 
computations, multithreading may not have much effect on execution time, and in some cases, it may even increase it slightly.

"""