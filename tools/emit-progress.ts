import * as fs from "node:fs/promises"
import * as path from "node:path";

void main();

async function main() {
    const out = {
        patches: await countPatchProgress(),
        sources: await countSourceProgress()
    }

    await fs.mkdir(path.join(import.meta.dirname, "../build/progress"), { recursive: true })
    await fs.writeFile(path.join(import.meta.dirname, "../build/progress/progress.json"), JSON.stringify(out))
}

async function countPatchProgress(): Promise<string> {
    const pending = await countFiles(path.join(import.meta.dirname, "../src/porting/patches"))
    const done = await countFiles(path.join(import.meta.dirname, "../src/porting/patched"))
    const partial = await countFiles(path.join(import.meta.dirname, "../src/porting/partially-patched"))
    const ignored = await countFiles(path.join(import.meta.dirname, "../src/porting/wont-patch"))

    const addresssed = done + partial + ignored
    const total = pending + addresssed

    return ((addresssed / total) * 100).toFixed(2)
}

async function countSourceProgress(): Promise<string> {
    const src = path.join(import.meta.dirname, "../src/main/paperJava")
    const total = await countFiles(src)
    const done = await countFiles(src, async (fp) => {
        const d = (await fs.readFile(fp)).toString()
        return d.includes("@Ported")
    });

    return ((done / total) * 100).toFixed(2)
}

async function countFiles(dir: string, filter: (fp: string) => (boolean | Promise<boolean>) = () => true): Promise<number> {
    let count = 0;
    const files = await fs.readdir(dir, { withFileTypes: true });

    for (const f of files) {
        if (f.isDirectory()) {
            count += await countFiles(path.join(dir, f.name), filter);
        }

        if (f.isFile()) {
            const fp = path.join(dir, f.name)
            if (await filter(fp)) {
                count++;
            }
        }
    }

    return count;
}
