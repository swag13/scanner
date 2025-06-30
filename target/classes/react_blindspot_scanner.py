# react_ai_blindspot_scanner.py
import os
import re
import json

def scan_file(filepath):
    results = []
    with open(filepath, 'r', encoding='utf-8', errors='ignore') as f:
        code = f.read()

        # 1. Hardcoded API URLs or tokens
        if re.search(r'https?://[^\s\'"]+', code):
            results.append("Hardcoded API URLs or tokens found.")

        # 2. Business logic inside UI component
        if re.search(r'(if\s*\(|for\s*\(|while\s*\()', code) and 'useState' in code:
            results.append("Possible business logic embedded in component.")

        # 3. Missing PropTypes
        if re.search(r'function\s+\w+\s*\(', code) and 'PropTypes' not in code:
            results.append("Component might be missing PropTypes validation.")

        # 4. No error boundaries
        if 'componentDidCatch' not in code and 'ErrorBoundary' not in code:
            results.append("Missing error boundary in component.")

        # 5. No accessibility attributes
        if '<button' in code and 'aria-' not in code:
            results.append("Buttons missing accessibility attributes.")

        # 6. Unused imports (simple check)
        imports = re.findall(r'import\s+(\w+)', code)
        for imp in imports:
            if imp not in code.split('\n', 1)[-1]:
                results.append(f"Unused import: {imp}")

    return results

def scan_directory(root_dir):
    issues = {}
    for dirpath, _, filenames in os.walk(root_dir):
        for file in filenames:
            if file.endswith(('.js', '.jsx')):
                path = os.path.join(dirpath, file)
                scan_result = scan_file(path)
                if scan_result:
                    issues[path] = scan_result
    return issues

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Scan React project for AI-blind spots")
    parser.add_argument("project_path", help="Path to React project")
    parser.add_argument("--output", default="react_scan_results.json", help="Output JSON file")
    args = parser.parse_args()

    results = scan_directory(args.project_path)

    with open(args.output, "w") as f:
        json.dump(results, f, indent=2)

    print(f"Scan complete. Results written to {args.output}")
