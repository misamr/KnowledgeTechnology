graph[
    node[
        id 1
        label "Which age group does the patient belong to?"
        display "radio"
    ]
    edge[
        source 1
        target 3
        label "18 to 49 years old"
    ]
    edge[
        source 1
        target 3
        label "50 years or older"
    ]

    node[
        id 2
        label "exit"
        display "terminate"
    ]

    node[
            id 3
            label "Does the patient suffer from any of the following conditions?"
            display "checkbox"
        ]
        edge[
            source 3
            target 4
            label "diabetes"
        ]
        edge[
            source 3
            target 4
            label "hypertension"
        ]
        edge[
            source 3
            target 4
            label "chronic pain"
        ]


        node[
                id 4
                label "Does the patient use any of the following medication?"
                display "checkbox"
            ]
            edge[
                source 4
                target 5
                label "warfarin"
            ]
            edge[
                source 4
                target 5
                label "vasocardin"
            ]
            edge[
                source 4
                target 6
                label "pain killers"
            ]

    node[
        id 5
        label "Does the patient exercise regularly?"
        display "radio"
    ]
    edge[
        source 5
        target 2
        label "yes"
    ]
    edge[
        source 5
        target 2
        label "no"
    ]

    node[
        id 6
        label "Does the patient have a sore throat?"
        display "radio"
    ]
    edge[
        source 6
        target 2
        label "yes"
    ]
    edge[
        source 6
        target 5
        label "no"
    ]





]


